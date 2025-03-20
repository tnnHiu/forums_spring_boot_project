package org.spring.mockprojectwebapp.services.implement;

import org.spring.mockprojectwebapp.entities.User;
import org.spring.mockprojectwebapp.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendVerificationEmail(User user, String token) {
        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl = "http://localhost:8080/verify-email?token=" + token;
        String message = "Please click the link below to verify your email address:";

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " \r\n" + confirmationUrl);
        mailSender.send(email);
    }
}