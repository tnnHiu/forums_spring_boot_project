package org.spring.mockprojectwebapp.dtos;

import lombok.Getter;

@Getter
public class LoginDto {
    // Getters và Setters
    private String username;
    private String email;
    private String password;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}