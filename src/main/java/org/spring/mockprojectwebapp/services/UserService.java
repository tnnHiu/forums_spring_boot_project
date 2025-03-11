package org.spring.mockprojectwebapp.services;

import org.spring.mockprojectwebapp.dtos.UserDTO;

public interface UserService {
    void registerUser(UserDTO userDTO);
}