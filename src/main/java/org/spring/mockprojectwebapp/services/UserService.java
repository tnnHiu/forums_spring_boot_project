package org.spring.mockprojectwebapp.services;

import org.spring.mockprojectwebapp.dtos.LoginDTO;
import org.spring.mockprojectwebapp.dtos.UserDTO;
import org.spring.mockprojectwebapp.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> login(LoginDTO loginDto);

    List<UserDTO> findAllUsers();

    UserDTO findUserById(Integer userId);

    boolean doesUserExist(Integer userId);

    void editUser(UserDTO updatedUserDTO, Integer userId);

    void deleteUserById(Integer userId);
}

