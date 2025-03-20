package org.spring.mockprojectwebapp.services;

import org.spring.mockprojectwebapp.entities.User;

public interface EmailService {
    void sendVerificationEmail(User user, String token);
    void sendPasswordResetEmail(User user, String token);
}

