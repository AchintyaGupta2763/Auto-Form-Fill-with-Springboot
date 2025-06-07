package com.srm.trackportal.dto;

import com.srm.trackportal.model.User;

public class LoginResponse {
    private Boolean success;
    private String token;
    private User user;

    public LoginResponse() {}

    public LoginResponse(Boolean success, String token, User user) {
        this.success = success;
        this.token = token;
        this.user = user;
    }

    public Boolean getSuccess() { return success; }
    public void setSuccess(Boolean success) { this.success = success; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
