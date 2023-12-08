package com.volunteerplatform.service.impl;

import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.volunteerplatform.dto.UserDTO;
import com.volunteerplatform.entity.Event;
import com.volunteerplatform.entity.User;
import com.volunteerplatform.repo.EventRepository;
import com.volunteerplatform.repo.UserRepository;
import com.volunteerplatform.service.UserService;

import javassist.NotFoundException;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final EventRepository eventRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, EventRepository eventRepository) {
		this.userRepository = userRepository;
		this.eventRepository = eventRepository;
	}

	@Override
	public UserDTO registerUser(UserDTO userDTO) {
		User userEntity = new User();
		userEntity.setUsername(userDTO.getUsername());
		userEntity.setPassword(userDTO.getPassword());
		userEntity.setEmail(userDTO.getEmail());
		userEntity.setAdmin(false);
		userEntity = userRepository.save(userEntity);

		UserDTO userdto = new UserDTO();
		userdto.setId(userEntity.getId());
		userdto.setEmail(userEntity.getEmail());
		userdto.setEnrolledEvents(userEntity.getEnrolledEvents());
		userdto.setIsLoggedIn(userEntity.isLoggedIn());
		userdto.setPassword(userEntity.getPassword());
		userdto.setUsername(userEntity.getUsername());

		return userdto;
	}

	@Override
	public UserDTO loginUser(UserDTO userDTO) {
		User userEntity = userRepository.findByEmail(userDTO.getEmail());
		boolean isMatch = userEntity != null && userEntity.getPassword().equals(userDTO.getPassword());
		userEntity.setIsLoggedIn(isMatch);
		UserDTO userdto = new UserDTO();
		userEntity = userRepository.save(userEntity);
		userdto.setId(userEntity.getId());
		userdto.setEmail(userEntity.getEmail());
		userdto.setEnrolledEvents(userEntity.getEnrolledEvents());
		userdto.setIsLoggedIn(userEntity.isLoggedIn());
		userdto.setPassword(userEntity.getPassword());
		userdto.setUsername(userEntity.getUsername());

		return userdto;
	}

	@Override
	public UserDTO logoutUser(UserDTO userDTO) {
		User userEntity = userRepository.findByEmail(userDTO.getEmail());
		boolean isMatch = userEntity != null && userEntity.getPassword().equals(userDTO.getPassword());
		userEntity.setIsLoggedIn(false);
		userEntity = userRepository.save(userEntity);
		UserDTO userdto = new UserDTO();
		userdto.setId(userEntity.getId());
		userdto.setEmail(userEntity.getEmail());
		userdto.setEnrolledEvents(userEntity.getEnrolledEvents());
		userdto.setIsLoggedIn(userEntity.isLoggedIn());
		userdto.setPassword(userEntity.getPassword());
		userdto.setUsername(userEntity.getUsername());

		return userdto;
	}

	@Override
	public void enrollForEvent(UserDTO userDTO, Long eventId) throws NotFoundException {
		// Implementation to enroll a user for an event
		try {
			Event eventEntity = eventRepository.findById(eventId)
					.orElseThrow(() -> new EntityNotFoundException("Event not found with ID: " + eventId));

			// Adding the user ID to the enrolledUserIds list in EventEntity
			Set<User> enrolledUsers = eventEntity.getEnrolledUserIds();
			enrolledUsers.add(userRepository.findByEmail(userDTO.getEmail()));
			eventEntity.setEnrolledUserIds(enrolledUsers);

			eventRepository.save(eventEntity);
		} catch (EntityNotFoundException e) {
			// Handle exception: Event not found
			throw new NotFoundException("Event not found with ID: " + eventId);
		}
	}
}
