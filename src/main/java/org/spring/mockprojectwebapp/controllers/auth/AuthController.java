package org.spring.mockprojectwebapp.controllers.auth;


import jakarta.validation.Valid;


import org.spring.mockprojectwebapp.dtos.admin.RegisterDTO;
import org.spring.mockprojectwebapp.services.implement.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@Controller
//@RequestMapping("/")
public class AuthController {

    @Autowired
    private AuthServiceImpl authService;

    @GetMapping("/login")
    public String showLoginPage() {
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
            authService.save(registerDTO);
            model.addAttribute("registerDTO", new RegisterDTO());
            model.addAttribute("success", true);
        } catch (Exception e) {
            result.addError(new FieldError("registerDTO", "username", e.getMessage()));
        }
        return "register";
    }
}