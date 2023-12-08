package com.volunteerplatform.utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.volunteerplatform.dto.EventDTO;
import com.volunteerplatform.dto.UserDTO;

public class MasterData {

	public static EventDTO getEventDTO() {
		EventDTO eventDTO = new EventDTO();
		eventDTO.setId(1L);
		eventDTO.setName("Sample Event");
		eventDTO.setDate(LocalDate.now());
		eventDTO.setTime(LocalTime.of(15, 30));
		eventDTO.setDescription("This is a sample event description");
		eventDTO.setEnrolledUserIds(new HashSet<>()); // Initialize with an empty set
		return eventDTO;
	}

	public static List<EventDTO> getEventDTOList() {
		List<EventDTO> eventDTOList = new ArrayList<>();
		eventDTOList.add(getEventDTO());
		return eventDTOList;
	}

	public static UserDTO getUserDTO() {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(1L);
		userDTO.setUsername("sample_user");
		userDTO.setPassword("sample_password");
		userDTO.setEmail("sample@example.com");
		userDTO.setLoggedIn(false);
		userDTO.setEnrolledEvents(new HashSet<>()); // Initialize with an empty set
		return userDTO;
	}

	public static List<UserDTO> getUserDTOList() {
		List<UserDTO> userDTOList = new ArrayList<>();
		userDTOList.add(getUserDTO());
		return userDTOList;
	}

	public static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule());
			mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
			final String jsonContent = mapper.writeValueAsString(obj);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String randomStringWithSize(int size) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < size; i++) {
			s.append("A");
		}
		return s.toString();
	}
}
