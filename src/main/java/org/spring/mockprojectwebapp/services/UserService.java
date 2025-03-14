package org.spring.mockprojectwebapp.services;

import org.spring.mockprojectwebapp.dtos.LoginDTO;
import org.spring.mockprojectwebapp.dtos.UserDTO;
import org.spring.mockprojectwebapp.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> login(LoginDTO loginDto);

    List<UserDTO> findAllUsers();

    Optional<User> findUserByEmail(String email);

    UserDTO findUserById(Integer userId);

    boolean doesUserExist(Integer userId);

    List<UserDTO> searchUsers(String keyword);

    void deleteUserById(Integer userId);

    void updateUserStatus(Integer userId, User.Status status);

    UserDTO mapToUserDTO(User user);
}