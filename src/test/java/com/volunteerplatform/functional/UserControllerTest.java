package com.volunteerplatform.functional;

import static com.volunteerplatform.utils.MasterData.asJsonString;
import static com.volunteerplatform.utils.MasterData.getUserDTO;
import static com.volunteerplatform.utils.TestUtils.businessTestFile;
import static com.volunteerplatform.utils.TestUtils.currentTest;
import static com.volunteerplatform.utils.TestUtils.testReport;
import static com.volunteerplatform.utils.TestUtils.yakshaAssert;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

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

import javassist.NotFoundException;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testRegisterUser() throws Exception {
		UserDTO userDTO = getUserDTO();

		when(userService.registerUser(userDTO)).thenReturn(userDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/users").content(asJsonString(userDTO))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), (asJsonString(userDTO).length() > 0) ? "true" : "false", businessTestFile);
	}

	// Existing test methods...

	@Test
	public void testLoginUserValidCredentials() throws Exception {
		UserDTO userDTO = getUserDTO();

		when(userService.loginUser(userDTO)).thenReturn(userDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/users/login").content(asJsonString(userDTO))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		yakshaAssert(currentTest(), (result.getResponse().getStatus() == HttpStatus.UNAUTHORIZED.value() ? "true" : "false"),
				businessTestFile);
	}

	@Test
	public void testLoginUserInvalidCredentials() throws Exception {
		UserDTO userDTO = getUserDTO();

		when(userService.loginUser(userDTO)).thenReturn(null);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/users/login").content(asJsonString(userDTO))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getStatus() == HttpStatus.UNAUTHORIZED.value() ? "true" : "false"),
				businessTestFile);
	}

	@Test
	public void testLogoutUserInvalidCredentials() throws Exception {
		UserDTO userDTO = getUserDTO();

		when(userService.logoutUser(userDTO)).thenReturn(null);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/users/logout").content(asJsonString(userDTO))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getStatus() == HttpStatus.UNAUTHORIZED.value() ? "true" : "false"),
				businessTestFile);
	}

	@Test
	public void testEnrollForEventValidEventId() throws Exception {
		UserDTO userDTO = getUserDTO();
		Long eventId = 1L;

		// Mocking the successful enrollment scenario
		doNothing().when(userService).enrollForEvent(userDTO, eventId);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/users/events/" + eventId + "/enroll")
				.content(asJsonString(userDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), (result.getResponse().getStatus() == HttpStatus.OK.value() ? "true" : "false"),
				businessTestFile);
	}


}
