package org.spring.mockprojectwebapp.services.implement;

import jakarta.persistence.EntityNotFoundException;
import org.spring.mockprojectwebapp.dtos.LoginDTO;
import org.spring.mockprojectwebapp.dtos.UserDTO;
import org.spring.mockprojectwebapp.entities.User;
import org.spring.mockprojectwebapp.repositories.UserRepository;
import org.spring.mockprojectwebapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> login(LoginDTO loginDTO) {
        return userRepository.findByEmail(loginDTO.getEmail()).filter(user -> user.getPassword().equals(loginDTO.getPassword()));
    }

    public void deleteUserById(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        userOptional.ifPresent(user -> {
            userRepository.delete(user);
        });
    }

    public boolean doesUserExist(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.isPresent();
    }

    public void editUser(UserDTO updatedUserDTO, Integer userId) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        existingUser.setStatus(updatedUserDTO.getStatus());

        userRepository.save(existingUser);
    }

    public UserDTO findUserById(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            return mapToUserDTO(userOptional.get());
        }
        return null;
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map((user) -> mapToUserDTO(user))
                .collect(Collectors.toList());
    }

    private UserDTO mapToUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        String[] str = user.getUsername().split(" ");
        userDTO.setStatus(user.getStatus());
        return userDTO;
    }

}