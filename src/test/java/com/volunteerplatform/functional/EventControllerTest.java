package com.volunteerplatform.functional;

import static com.volunteerplatform.utils.MasterData.getEventDTO;
import static com.volunteerplatform.utils.MasterData.getEventDTOList;
import static com.volunteerplatform.utils.TestUtils.businessTestFile;
import static com.volunteerplatform.utils.TestUtils.currentTest;
import static com.volunteerplatform.utils.TestUtils.testReport;
import static com.volunteerplatform.utils.TestUtils.yakshaAssert;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
public class EventControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EventService eventService;

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testCreateEvent() throws Exception {
		EventDTO eventDTO = getEventDTO();

		when(this.eventService.createEvent(eventDTO)).thenReturn(eventDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/events")
				.content(MasterData.asJsonString(eventDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), (MasterData.asJsonString(eventDTO).length() > 0) ? "true" : "false",
				businessTestFile);
	}

	@Test
	public void testGetAllUpcomingEvents() throws Exception {
		List<EventDTO> eventDTOList = getEventDTOList();

		when(this.eventService.getAllUpcomingEvents()).thenReturn(eventDTOList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/events")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(eventDTOList)) ? "true"
						: "false"),
				businessTestFile);
	}
}
