package org.spring.mockprojectwebapp.services.implement;

import jakarta.persistence.EntityNotFoundException;
import org.spring.mockprojectwebapp.dtos.admin.UserDTO;
import org.spring.mockprojectwebapp.dtos.user.UserSearchDTO;
import org.spring.mockprojectwebapp.entities.User;
import org.spring.mockprojectwebapp.repositories.UserRepository;
import org.spring.mockprojectwebapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public Page<User> getAccounts(String keyword, Pageable pageable) {
        if (keyword != null && !keyword.isEmpty()) {
            return userRepository.findByUsernameContainingIgnoreCase(keyword, pageable);
        }
        return userRepository.findAll(pageable);
    }


    @Override
    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::mapToUserDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO findUserById(int userId) {
        User user = userRepository.findByUserId(userId);
        return mapToUserDTO(user);
    }


    @Override
    public List<UserDTO> searchUsers(String keyword) {
        List<User> users = userRepository.findByUsernameContainingOrEmailContaining(keyword);
        return users.stream().map(this::mapToUserDTO).collect(Collectors.toList());
    }

    @Override
    public Page<UserSearchDTO> searchUsersWithPageable (String keyword, Pageable pageable) {
        Page<User> userPage = userRepository.findByUsernameContainingIgnoreCase(keyword, pageable);
        return userPage.map(this::mapToUserSearchDTO);
    }

    @Override
    public void deleteUserById(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        userOptional.ifPresent(userRepository::delete);
    }

    @Override
    public boolean doesUserExist(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.isPresent();
    }

    public void updateUserStatus(Integer userId, User.Status newStatus) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setStatus(newStatus);
        userRepository.save(user);
    }

    // Chuyển đổi từ Entity sang DTO
    @Override
    public UserDTO mapToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setStatus(user.getStatus());

        return userDTO;
    }

    private UserSearchDTO mapToUserSearchDTO(User user) {
        return UserSearchDTO.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .status(user.getStatus().ordinal())
                .build();
    }
}