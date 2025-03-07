package org.spring.mockprojectwebapp.services;

import org.spring.mockprojectwebapp.dtos.UserDto;
import org.spring.mockprojectwebapp.entities.User;
import org.spring.mockprojectwebapp.entities.User.Status;
import org.spring.mockprojectwebapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User register(UserDto userDto) {
        // Kiểm tra trùng email
        if (userRepository.findByEmail(userDto.getEmail()) != null) {
            throw new RuntimeException("Email đã tồn tại");
        }
        // Kiểm tra trùng username
        if (userRepository.findByUsername(userDto.getUsername()) != null) {
            throw new RuntimeException("Username đã tồn tại");
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        user.setPassword(encodedPassword);
        user.setRoleId(1);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setStatus(Status.active);
        return userRepository.save(user);
    }
////    @Override
//    public User login(String username, String password) {
//        User user = userRepository.findByUsername(username);
//        if (user == null) {
//            throw new RuntimeException("Username không tồn tại");
//        }
//        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
//            throw new RuntimeException("Mật khẩu không đúng");
//        }
//        return user;
//    }
}
