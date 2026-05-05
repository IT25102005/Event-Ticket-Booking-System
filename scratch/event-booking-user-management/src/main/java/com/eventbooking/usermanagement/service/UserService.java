package com.eventbooking.usermanagement.service;

import com.eventbooking.usermanagement.dto.UpdateUserDTO;
import com.eventbooking.usermanagement.dto.UserResponseDTO;
import com.eventbooking.usermanagement.model.UserRole;
import com.eventbooking.usermanagement.model.AccountStatus;

import java.util.List;

// Abstraction: Interface hides implementation details
public interface UserService {
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO getUserById(Long userId);
    List<UserResponseDTO> searchUsers(String keyword, UserRole role, AccountStatus status);
    UserResponseDTO updateUserProfile(Long userId, UpdateUserDTO updateDTO);
    void changePassword(Long userId, String currentPassword, String newPassword);
    void updateAccountStatus(Long userId, AccountStatus status);
    void updateUserRole(Long userId, UserRole role);
    void deleteUser(Long userId);
}
