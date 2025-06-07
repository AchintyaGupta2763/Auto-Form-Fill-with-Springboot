package com.srm.trackportal.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin; 
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.srm.trackportal.dto.ApiResponse;
import com.srm.trackportal.dto.LoginRequest;
import com.srm.trackportal.dto.LoginResponse;
import com.srm.trackportal.dto.SignupRequest;
import com.srm.trackportal.model.User;
import com.srm.trackportal.service.UserService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signup(@Valid @RequestBody SignupRequest signUpRequest) {
        String msg = userService.registerUser(signUpRequest);
        if (msg.startsWith("Error")) {
            return ResponseEntity
                .badRequest()
                .body(new ApiResponse(false, msg));
        }
        return ResponseEntity.ok(new ApiResponse(true, msg));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            // Authenticate and generate JWT
            String jwt = userService.authenticateUser(loginRequest);

            // Fetch user details
            User user = userService.findByEmail(loginRequest.getEmail())
                        .orElseThrow(() -> new RuntimeException("User not found"));

            LoginResponse resp = new LoginResponse(true, jwt, user);
            return ResponseEntity.ok(resp);

        } catch (Exception e) {
            // Optional: Log exception class and message for traceability
            System.err.println("Login error: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            return ResponseEntity
                .status(401)
                .body(new ApiResponse(false, "Invalid email or password"));
        }
    }
}
