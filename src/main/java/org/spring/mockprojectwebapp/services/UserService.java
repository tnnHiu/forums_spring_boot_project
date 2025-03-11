package org.spring.mockprojectwebapp.services;

import org.spring.mockprojectwebapp.entities.User;

public interface UserService {
    User login(String email, String password);
}