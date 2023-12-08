package com.volunteerplatform.service;

import com.volunteerplatform.dto.UserDTO;

import javassist.NotFoundException;

public interface UserService {

	UserDTO registerUser(UserDTO userDTO);

	UserDTO loginUser(UserDTO userDTO);

	UserDTO logoutUser(UserDTO userDTO);

	void enrollForEvent(UserDTO userDTO, Long eventId) throws NotFoundException;

}
