package org.spring.mockprojectwebapp.services.implement;

import org.spring.mockprojectwebapp.dtos.LoginDTO;
import org.spring.mockprojectwebapp.entities.User;
import org.spring.mockprojectwebapp.repositories.UserRepository;
import org.spring.mockprojectwebapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User login(String email, String password) {
        return userRepository.findByEmail(email);
    }
//
//    @Override
//    public User login() {
//        return userRepository.findByEmail(loginDto.getEmail()).filter(user -> user
//                .getPassword().equals(loginDto.getPassword()));
//    }'

}

