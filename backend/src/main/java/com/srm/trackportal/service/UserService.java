package com.srm.trackportal.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.srm.trackportal.config.JwtTokenProvider;
import com.srm.trackportal.dto.LoginRequest;
import com.srm.trackportal.dto.SignupRequest;
import com.srm.trackportal.model.User;
import com.srm.trackportal.repository.UserRepository;

@Service
public class UserService {

    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtTokenProvider tokenProvider;

    public String authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), 
                loginRequest.getPassword()
            )
        );
        return tokenProvider.generateToken(authentication);
    }

    public String registerUser(SignupRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return "Error: Email is already in use!";
        }
        if (userRepository.existsByRegistrationNumber(signUpRequest.getRegistrationNumber())) {
            return "Error: Registration Number is already in use!";
        }
        // Create user
        User user = new User(
            signUpRequest.getName(),
            signUpRequest.getRegistrationNumber(),
            signUpRequest.getEmail(),
            passwordEncoder.encode(signUpRequest.getPassword()),
            signUpRequest.getTrack()
        );
        userRepository.save(user);
        return "User registered successfully!";
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}