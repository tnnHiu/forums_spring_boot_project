package org.spring.mockprojectwebapp.services;

import org.spring.mockprojectwebapp.dtos.user.UpdatePasswordDTO;
import org.spring.mockprojectwebapp.dtos.user.UpdateUsernameDTO;
import org.spring.mockprojectwebapp.entities.User;

public interface ProfileService {
    User getCurrentUser(String email);
    void updateUsername(String email, UpdateUsernameDTO updateUsernameDTO);
    String updatePassword(String email, UpdatePasswordDTO updatePasswordDTO);
}