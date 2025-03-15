package org.spring.mockprojectwebapp.services;

import org.spring.mockprojectwebapp.dtos.UserDTO;
import org.spring.mockprojectwebapp.entities.User;

public interface UserService {
    void updateUserStatus(int userId, User.Status status);
}