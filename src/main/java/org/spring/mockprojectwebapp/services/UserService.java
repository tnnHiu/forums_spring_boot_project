package org.spring.mockprojectwebapp.services;

import org.spring.mockprojectwebapp.dtos.UserDto;
import org.spring.mockprojectwebapp.entities.User;

public interface UserService {
    User register(UserDto userDto);

//    User login(String username, String password);
}
