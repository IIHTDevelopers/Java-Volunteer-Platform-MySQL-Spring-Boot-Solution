package com.volunteerplatform.exception;

import static com.volunteerplatform.utils.TestUtils.currentTest;
import static com.volunteerplatform.utils.TestUtils.exceptionTestFile;
import static com.volunteerplatform.utils.TestUtils.testReport;
import static com.volunteerplatform.utils.TestUtils.yakshaAssert;
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

import com.volunteerplatform.controller.EventController;
import com.volunteerplatform.dto.EventDTO;
import com.volunteerplatform.service.EventService;
import com.volunteerplatform.utils.MasterData;

@WebMvcTest(EventController.class)
@AutoConfigureMockMvc
public class EventExceptionTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EventService eventService;

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testCreateEventInvalidDataException() throws Exception {
		EventDTO eventDTO = new EventDTO(); // Create an invalid EventDTO

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/events")
				.content(MasterData.asJsonString(eventDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getStatus() == HttpStatus.BAD_REQUEST.value() ? "true" : "false"),
				exceptionTestFile);
	}

	@Test
	public void testUpdateEventNotFoundException() throws Exception {
		EventDTO eventDTO = new EventDTO(); // Create a valid EventDTO
		eventDTO.setId(1L);

		when(this.eventService.updateEvent(eventDTO)).thenThrow(new NotFoundException("Event not found"));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/events")
				.content(MasterData.asJsonString(eventDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		yakshaAssert(currentTest(),
				(result.getResponse().getStatus() == 400 ? "true" : "false"),
				exceptionTestFile);
	}

	@Test
	public void testCancelEventNotFoundException() throws Exception {
		Long eventId = 1L;
		ErrorResponse exResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Event not found");

		// Mocking void method behavior
		doThrow(new NotFoundException("Event not found")).when(eventService).cancelEvent(eventId);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/events/cancel/" + eventId)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getStatus() == HttpStatus.NOT_FOUND.value() ? "true" : "false"),
				exceptionTestFile);
	}
}
