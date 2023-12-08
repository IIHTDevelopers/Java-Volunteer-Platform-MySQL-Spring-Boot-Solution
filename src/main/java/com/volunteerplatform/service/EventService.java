package com.volunteerplatform.service;

import java.util.List;

import com.volunteerplatform.dto.EventDTO;

import javassist.NotFoundException;

public interface EventService {

	EventDTO createEvent(EventDTO eventDTO);

	EventDTO updateEvent(EventDTO eventDTO) throws NotFoundException;

	void cancelEvent(Long eventId) throws NotFoundException;

	List<EventDTO> getAllUpcomingEvents();
}
