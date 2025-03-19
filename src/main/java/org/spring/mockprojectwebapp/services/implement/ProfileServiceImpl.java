package org.spring.mockprojectwebapp.services.implement;

import org.spring.mockprojectwebapp.dtos.user.UpdatePasswordDTO;
import org.spring.mockprojectwebapp.dtos.user.UpdateUsernameDTO;
import org.spring.mockprojectwebapp.entities.User;
import org.spring.mockprojectwebapp.repositories.UserRepository;
import org.spring.mockprojectwebapp.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User getCurrentUser(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void updateUsername(String email, UpdateUsernameDTO updateUsernameDTO) {
        // Tìm người dùng bằng email
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found"); // Nếu không tìm thấy người dùng
        }

        // Cập nhật username
        user.setUsername(updateUsernameDTO.getUsername());
        userRepository.save(user); // Lưu thay đổi vào database
    }

    @Override
    public String updatePassword(String email, UpdatePasswordDTO updatePasswordDTO) {
        if (email == null || updatePasswordDTO == null) {
            throw new IllegalArgumentException("Email or DTO cannot be null");
        }

        User user = getCurrentUser(email);
        if (user == null) {
            throw new RuntimeException("Không tìm thấy người dùng");
        }

        if (!passwordEncoder.matches(updatePasswordDTO.getOldPassword(), user.getPassword())) {
            return "Mật khẩu cũ không chính xác";
        }

        if (!updatePasswordDTO.getPassword().equals(updatePasswordDTO.getConfirmPassword())) {
            return "Mật khẩu mới không trùng khớp";
        }

        user.setPassword(passwordEncoder.encode(updatePasswordDTO.getPassword()));
        userRepository.save(user);
        return null; // Trả về null nếu không có lỗi
    }
}