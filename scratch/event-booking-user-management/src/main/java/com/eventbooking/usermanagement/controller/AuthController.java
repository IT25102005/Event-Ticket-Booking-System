package com.eventbooking.usermanagement.controller;

import com.eventbooking.usermanagement.dto.LoginRequestDTO;
import com.eventbooking.usermanagement.dto.RegisterRequestDTO;
import com.eventbooking.usermanagement.dto.UserResponseDTO;
import com.eventbooking.usermanagement.model.UserRole;
import com.eventbooking.usermanagement.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // For development purposes
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerCustomer(@Valid @RequestBody RegisterRequestDTO registerDTO) {
        UserResponseDTO response = authService.register(registerDTO, UserRole.CUSTOMER);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/register-admin")
    public ResponseEntity<UserResponseDTO> registerAdmin(@Valid @RequestBody RegisterRequestDTO registerDTO) {
        UserResponseDTO response = authService.register(registerDTO, UserRole.ADMIN);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginDTO) {
        UserResponseDTO response = authService.login(loginDTO);
        return ResponseEntity.ok(response);
    }
}
