// backend/src/main/java/com/srm/trackportal/dto/SignupRequest.java
package com.srm.trackportal.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.srm.trackportal.model.Track;

public class SignupRequest {

    @NotBlank 
    private String name;

    @NotBlank
    private String registrationNumber;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 100)
    private String password;

    private Track track;

    // Jackson requires a no-arg constructor
    public SignupRequest() {}

    public SignupRequest(String name, String registrationNumber, String email, String password, Track track) {
        this.name = name;
        this.registrationNumber = registrationNumber;
        this.email = email;
        this.password = password;
        this.track = track;
    }

    // Getters & Setters

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

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

    public Track getTrack() {
        return track;
    }
    public void setTrack(Track track) {
        this.track = track;
    }
}
