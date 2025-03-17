package org.spring.mockprojectwebapp.services;

import org.spring.mockprojectwebapp.dtos.UserDTO;
import org.spring.mockprojectwebapp.entities.User;

import java.util.List;

public interface UserService {
    void updateUserStatus(int userId, User.Status status);
//    void updateUserStatus(int userId, User.Status status);

    List<UserDTO> findAllUsers();

    boolean doesUserExist(Integer userId);

    List<UserDTO> searchUsers(String keyword);

    void deleteUserById(Integer userId);

    void updateUserStatus(Integer userId, User.Status status);

    UserDTO mapToUserDTO(User user);
}