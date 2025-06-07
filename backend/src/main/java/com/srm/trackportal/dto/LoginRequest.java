// backend/src/main/java/com/srm/trackportal/dto/LoginRequest.java

package com.srm.trackportal.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    // Jackson requires a no-arg constructor
    public LoginRequest() {}

    public LoginRequest(String email, String password) {
        System.out.println("LoginRequest constructor called with email: " + email);
        System.out.println("LoginRequest constructor called with password: " + password);
        this.email = email;
        this.password = password;
    }

    // Getters & Setters

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
