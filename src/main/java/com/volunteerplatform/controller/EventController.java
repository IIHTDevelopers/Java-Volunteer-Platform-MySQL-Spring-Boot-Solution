package com.volunteerplatform.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.volunteerplatform.dto.EventDTO;
import com.volunteerplatform.service.EventService;

import javassist.NotFoundException;

@RestController
@RequestMapping("/api/events")
public class EventController {

	private final EventService eventService;

	@Autowired
	public EventController(EventService eventService) {
		this.eventService = eventService;
	}

	@GetMapping()
	public ResponseEntity<List<EventDTO>> getAllUpcomingEvents() {
		List<EventDTO> events = eventService.getAllUpcomingEvents();
		return new ResponseEntity<>(events, HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<EventDTO> createEvent(@RequestBody @Valid EventDTO eventDTO) {
		EventDTO createdEvent = eventService.createEvent(eventDTO);
		return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
	}

	@PutMapping()
	public ResponseEntity<EventDTO> updateEvent(@RequestBody @Valid EventDTO eventDTO) {
		EventDTO updatedEvent;
		try {
			updatedEvent = eventService.updateEvent(eventDTO);
			return new ResponseEntity<>(updatedEvent, HttpStatus.OK);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/cancel/{eventId}")
	public ResponseEntity<Void> cancelEvent(@PathVariable Long eventId) {
		try {
			eventService.cancelEvent(eventId);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (NotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
