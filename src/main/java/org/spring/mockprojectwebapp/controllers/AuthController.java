package org.spring.mockprojectwebapp.controllers;


import org.spring.mockprojectwebapp.dtos.LoginDTO;


import org.spring.mockprojectwebapp.entities.User;
import org.spring.mockprojectwebapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("loginDto", new LoginDTO());
        return "sign-in";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("loginDto") LoginDTO loginDto, Model model) {
        Optional<User> user = userService.login(loginDto);
        if (user.isPresent()) {
            return "redirect:/admin";
        } else {
            model.addAttribute("error", "Email hoặc mật khẩu không đúng!");
            return "redirect:/auth/login";
        }
    }


}