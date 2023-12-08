package com.volunteerplatform.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.volunteerplatform.dto.EventDTO;
import com.volunteerplatform.entity.Event;
import com.volunteerplatform.repo.EventRepository;
import com.volunteerplatform.service.EventService;

import javassist.NotFoundException;

@Service
public class EventServiceImpl implements EventService {

	private final EventRepository eventRepository;

	@Autowired
	public EventServiceImpl(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}

	@Override
	public EventDTO createEvent(EventDTO eventDTO) {
		// Implementation to create an event
		Event eventEntity = new Event();
		eventEntity.setName(eventDTO.getName());
		eventEntity.setDate(eventDTO.getDate());
		eventEntity.setTime(eventDTO.getTime());
		eventEntity.setDescription(eventDTO.getDescription());

		Event savedEvent = eventRepository.save(eventEntity);

		return convertToEventDTO(savedEvent);
	}

	@Override
	public EventDTO updateEvent(EventDTO eventDTO) throws NotFoundException {
		// Implementation to update an event
		Event eventEntity = eventRepository.findById(eventDTO.getId())
				.orElseThrow(() -> new NotFoundException("Event not found with ID: " + eventDTO.getId()));

		eventEntity.setName(eventDTO.getName());
		eventEntity.setDate(eventDTO.getDate());
		eventEntity.setTime(eventDTO.getTime());
		eventEntity.setDescription(eventDTO.getDescription());

		Event updatedEvent = eventRepository.save(eventEntity);

		return convertToEventDTO(updatedEvent);
	}

	@Override
	public void cancelEvent(Long eventId) throws NotFoundException {
		try {
			Event eventEntity = eventRepository.findById(eventId)
					.orElseThrow(() -> new EntityNotFoundException("Event not found with ID: " + eventId));

			eventRepository.delete(eventEntity);
		} catch (EntityNotFoundException e) {
			// Handle exception: Event not found
			throw new NotFoundException("Event not found with ID: " + eventId);
		}
	}

	// Helper method to convert EventEntity to EventDTO
	private EventDTO convertToEventDTO(Event eventEntity) {
		EventDTO eventDTO = new EventDTO();
		eventDTO.setId(eventEntity.getId());
		eventDTO.setName(eventEntity.getName());
		eventDTO.setDate(eventEntity.getDate());
		eventDTO.setTime(eventEntity.getTime());
		eventDTO.setDescription(eventEntity.getDescription());
		eventDTO.setEnrolledUserIds(eventEntity.getEnrolledUserIds());
		return eventDTO;
	}

	@Override
	public List<EventDTO> getAllUpcomingEvents() {
		LocalDate today = LocalDate.now();

		List<Event> upcomingEvents = eventRepository.findAll().stream().filter(event -> event.getDate().isAfter(today))
				.collect(Collectors.toList());

		return convertToEventDTOList(upcomingEvents);
	}

	private List<EventDTO> convertToEventDTOList(List<Event> eventEntities) {
		return eventEntities.stream().map(this::convertToEventDTO).collect(Collectors.toList());
	}

}
