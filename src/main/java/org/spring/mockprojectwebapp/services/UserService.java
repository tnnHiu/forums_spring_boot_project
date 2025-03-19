package org.spring.mockprojectwebapp.services;

import org.spring.mockprojectwebapp.dtos.admin.UserDTO;
import org.spring.mockprojectwebapp.dtos.user.UserSearchDTO;
import org.spring.mockprojectwebapp.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    List<UserDTO> findAllUsers();

    boolean doesUserExist(Integer userId);

    List<UserDTO> searchUsers(String keyword);

    Page<UserSearchDTO> searchUsersWithPageable(String keyword, Pageable pageable);

    void deleteUserById(Integer userId);

    void updateUserStatus(Integer userId, User.Status status);

    UserDTO mapToUserDTO(User user);

    UserDTO findUserById(int userId);

    Page<User> getAccounts(String keyword, Pageable pageable);
}