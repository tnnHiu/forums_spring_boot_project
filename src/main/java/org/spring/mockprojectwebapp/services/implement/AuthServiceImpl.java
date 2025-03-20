package org.spring.mockprojectwebapp.services.implement;

import org.spring.mockprojectwebapp.dtos.admin.RegisterDTO;
import org.spring.mockprojectwebapp.entities.Role;
import org.spring.mockprojectwebapp.entities.User;
import org.spring.mockprojectwebapp.repositories.RoleRepository;
import org.spring.mockprojectwebapp.repositories.UserRepository;
import org.spring.mockprojectwebapp.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService, UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email);
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(String.valueOf(user.getRole().getRoleName()))
                .accountLocked(user.getStatus() == User.Status.BANNED)
                .disabled(user.getStatus() == User.Status.INACTIVE)
                .build();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User save(RegisterDTO registerDTO) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        Role role = roleRepository.getRoleByRoleId(2);
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(registerDTO.getPassword()));
        user.setRole(role);
        user.setStatus(User.Status.INACTIVE);
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }
}
