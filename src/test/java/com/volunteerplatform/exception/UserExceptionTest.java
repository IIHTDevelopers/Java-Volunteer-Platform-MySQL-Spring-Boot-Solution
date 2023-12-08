package com.volunteerplatform.exception;

import static com.volunteerplatform.utils.TestUtils.currentTest;
import static com.volunteerplatform.utils.TestUtils.exceptionTestFile;
import static com.volunteerplatform.utils.TestUtils.testReport;
import static com.volunteerplatform.utils.TestUtils.yakshaAssert;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.volunteerplatform.controller.UserController;
import com.volunteerplatform.dto.UserDTO;
import com.volunteerplatform.service.UserService;
import com.volunteerplatform.utils.MasterData;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class UserExceptionTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testRegisterUserInvalidDataException() throws Exception {
		UserDTO userDTO = new UserDTO(); // Create an invalid UserDTO

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/users")
				.content(MasterData.asJsonString(userDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		yakshaAssert(currentTest(),
				(result.getResponse().getStatus() == HttpStatus.BAD_REQUEST.value() ? "true" : "false"),
				exceptionTestFile);
	}

	@Test
	public void testEnrollForEventNotFoundException() throws Exception {
		Long eventId = 1324L;
		ErrorResponse exResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Event not found");

		// Mocking void method behavior
		doThrow(new NotFoundException("Event not found")).when(userService).enrollForEvent(any(), eq(eventId));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/users/events/" + eventId + "/enroll")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		yakshaAssert(currentTest(),
				(result.getResponse().getStatus() == 400 ? "true" : "false"),
				exceptionTestFile);
	}

}
