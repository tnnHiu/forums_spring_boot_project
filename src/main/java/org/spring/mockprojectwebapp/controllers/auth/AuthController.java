package org.spring.mockprojectwebapp.controllers.auth;


import jakarta.validation.Valid;


import org.spring.mockprojectwebapp.dtos.admin.RegisterDTO;
import org.spring.mockprojectwebapp.entities.User;
import org.spring.mockprojectwebapp.services.EmailService;
import org.spring.mockprojectwebapp.services.UserService;
import org.spring.mockprojectwebapp.services.implement.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
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
        // Validate empty fields
        //boolean hasErrors = false;
        
        if (StringUtils.isEmpty(registerDTO.getUsername())) {
            result.rejectValue("username", "error.username", "Vui lòng nhập họ và tên");
            //hasErrors = true;
        }
        
        if (StringUtils.isEmpty(registerDTO.getEmail())) {
            result.rejectValue("email", "error.email", "Vui lòng nhập email");
            //hasErrors = true;
        }
        
        if (StringUtils.isEmpty(registerDTO.getPassword())) {
            result.rejectValue("password", "error.password", "Vui lòng nhập mật khẩu");
            //hasErrors = true;
        }
        
        if (StringUtils.isEmpty(registerDTO.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "error.confirmPassword", "Vui lòng xác nhận mật khẩu");
            //hasErrors = true;
        }

        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            //result.addError(new FieldError("registerDTO", "confirmPassword", "Passwords do not match"));
            result.rejectValue("confirmPassword", "error.confirmPassword", "Mật khẩu không khớp, vui lòng thử lại");
        }
        if (authService.findByEmail(registerDTO.getEmail()) != null) {
            //result.addError(new FieldError("registerDTO", "email", "Email address already in use"));
            result.rejectValue("email", "error.email", "Email đã tồn tại, vui lòng thử lại");
        }
        // if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
        //     result.addError(new FieldError("registerDTO", "confirmPassword", "Passwords do not match"));
        // }
        // if (authService.findByEmail(registerDTO.getEmail()) != null) {
        //     result.addError(new FieldError("registerDTO", "email", "Email address already in use"));
        // }
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
            model.addAttribute("message", "Tài Khoản của bạn đã được xác minh thành công.");
            return "verified";
        } else {
            model.addAttribute("message", "Token xác minh tài khoản không đúng. Vui lòng thử lại.");
            return "verify-email";
        }
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordPage() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, Model model) {
        User user = authService.findByEmail(email);
        if (user == null) {
            model.addAttribute("errorMessage", "Email không tồn tại.");
            return "forgot-password";
        }
        String token = UUID.randomUUID().toString();
        userService.createPasswordResetToken(user, token);
        emailService.sendPasswordResetEmail(user, token);
        model.addAttribute("message", "Email đặt lại mật khẩu đã được gửi.");
        return "forgot-password";
    }


    @GetMapping("/reset-password")
    public String showResetPasswordPage(@RequestParam("token") String token, Model model) {
        String result = userService.validatePasswordResetToken(token);
        if (result == null || "invalid".equals(result) || "expired".equals(result)){
            model.addAttribute("errorMessage", "Token không hợp lệ hoặc đã hết hạn.");
            return "reset-password";
        }
        model.addAttribute("token", token);
        return "reset-password";
    }


    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam("token") String token,
                                       @RequestParam("password") String password,
                                       @RequestParam("confirmPassword") String confirmPassword,
                                       Model model) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("errorMessage", "Mật khẩu không khớp.");
            return "reset-password";
        }
        String result = userService.validatePasswordResetToken(token);
        if (result == null || "invalid".equals(result) || "expired".equals(result)) {
            model.addAttribute("errorMessage", "Token không hợp lệ hoặc đã hết hạn.");
            return "reset-password";
        }
        User user = userService.getUserByPasswordResetToken(token);
        if (user == null) {
            model.addAttribute("errorMessage", "Người dùng không tồn tại.");
            return "reset-password";
        }
        userService.changeUserPassword(user, password);
        model.addAttribute("message", "Mật khẩu của bạn đã được đặt lại thành công.");
        return "login";
    }
}