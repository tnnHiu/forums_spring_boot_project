package org.spring.mockprojectwebapp.services.implement;

import jakarta.persistence.EntityNotFoundException;
import org.spring.mockprojectwebapp.dtos.UserDTO;
import org.spring.mockprojectwebapp.entities.User;
import org.spring.mockprojectwebapp.repositories.UserRepository;
import org.spring.mockprojectwebapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

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

//    @Override
//    public void updateUserStatus(int userId, User.Status status) {
//        User existingUser = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
//
//        // Cập nhật trạng thái và thời gian cập nhật
//        existingUser.setStatus(status);
//        existingUser.setUpdatedAt(LocalDateTime.now());
//
//        // Lưu vào cơ sở dữ liệu
//        userRepository.save(existingUser);
//    }

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
}