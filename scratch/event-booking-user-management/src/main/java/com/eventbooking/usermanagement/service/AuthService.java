package com.eventbooking.usermanagement.service;

import com.eventbooking.usermanagement.dto.LoginRequestDTO;
import com.eventbooking.usermanagement.dto.RegisterRequestDTO;
import com.eventbooking.usermanagement.dto.UserResponseDTO;
import com.eventbooking.usermanagement.model.UserRole;

public interface AuthService {
    UserResponseDTO register(RegisterRequestDTO registerDTO, UserRole role);
    UserResponseDTO login(LoginRequestDTO loginDTO);
}
