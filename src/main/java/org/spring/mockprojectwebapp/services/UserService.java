package org.spring.mockprojectwebapp.services;


import org.spring.mockprojectwebapp.dtos.LoginDto;
import org.spring.mockprojectwebapp.entities.User;

import java.util.Optional;

public interface UserService {
    Optional<User> login(LoginDto loginDto);
}
