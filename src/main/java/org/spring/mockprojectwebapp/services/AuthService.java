package org.spring.mockprojectwebapp.services;

import org.spring.mockprojectwebapp.dtos.RegisterDTO;
import org.spring.mockprojectwebapp.entities.User;

public interface AuthService {
    User findByEmail(String email);
    User save(RegisterDTO registerDTO);
}