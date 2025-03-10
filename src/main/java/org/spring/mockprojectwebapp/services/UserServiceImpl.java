package org.spring.mockprojectwebapp.services;


import org.spring.mockprojectwebapp.dtos.LoginDto;
import org.spring.mockprojectwebapp.entities.User;
import org.spring.mockprojectwebapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> login(LoginDto loginDto) {
        return userRepository.findByEmail(loginDto.getEmail())
                .filter(user -> user.getPassword().equals(loginDto.getPassword()));
    }
}
