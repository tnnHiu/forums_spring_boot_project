package org.spring.mockprojectwebapp.controllers;


import jakarta.validation.Valid;


import org.spring.mockprojectwebapp.dtos.admin.RegisterDTO;
import org.spring.mockprojectwebapp.entities.User;
import org.spring.mockprojectwebapp.services.EmailService;
import org.spring.mockprojectwebapp.services.UserService;
import org.spring.mockprojectwebapp.services.implement.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
//@RequestMapping("/")
public class AuthController {

    @Autowired
    private AuthServiceImpl authService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "status", required = false) String status,
                                Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Sai tên đăng nhập hoặc mật khẩu.");
        }
        if ("inactive" .equals(status)) {
            model.addAttribute("statusMessage", "Tài khoản của bạn chưa được kích hoạt. Vui lòng kiểm tra email.");
        } else if ("banned" .equals(status)) {
            model.addAttribute("statusMessage", "Tài khoản của bạn đã bị cấm. Vui lòng liên hệ hỗ trợ.");
        }
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {

        RegisterDTO registerDTO = new RegisterDTO();

        model.addAttribute("registerDTO", registerDTO);
        model.addAttribute("success", false);
        return "register";
    }

    @PostMapping("/register")
    public String register(Model model, @Valid @ModelAttribute RegisterDTO registerDTO, BindingResult result) {
        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            result.addError(new FieldError("registerDTO", "confirmPassword", "Passwords do not match"));
        }
        if (authService.findByEmail(registerDTO.getEmail()) != null) {
            result.addError(new FieldError("registerDTO", "email", "Email address already in use"));
        }
        if (result.hasErrors()) {
            return "register";
        }
        try {
            User user = authService.save(registerDTO);
            String token = UUID.randomUUID().toString();
            userService.createVerificationToken(user, token);
            emailService.sendVerificationEmail(user, token);
            model.addAttribute("registerDTO", new RegisterDTO());
            model.addAttribute("success", true);
        } catch (Exception e) {
            result.addError(new FieldError("registerDTO", "username", e.getMessage()));
        }
        return "register";
    }

    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam("token") String token, Model model) {
        String result = userService.validateVerificationToken(token);
        if (result.equals("valid")) {
            model.addAttribute("message", "Your account has been verified successfully.");
            return "verified";
        } else {
            model.addAttribute("message", "Invalid verification token.");
            return "verify-email";
        }
    }
}