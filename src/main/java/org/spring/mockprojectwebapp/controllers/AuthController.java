package org.spring.mockprojectwebapp.controllers;


import org.spring.mockprojectwebapp.dtos.LoginDTO;

import jakarta.validation.Valid;
import org.spring.mockprojectwebapp.dtos.UserDTO;
import org.spring.mockprojectwebapp.entities.User;
import org.spring.mockprojectwebapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("loginDTO", new LoginDTO());
        return "sign-in";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("loginDto") LoginDTO loginDto, Model model) {
        Optional<User> user = userService.login(loginDTO);
        if (user.isPresent()) {
            return "redirect:/admin";
        } else {
            model.addAttribute("error", "Email hoặc mật khẩu không đúng!");
            return "redirect:/auth/login";
        }
    }

    @GetMapping("/users")
    public String users(Model model) {
        List<UserDTO> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "user";
    }

    @GetMapping("/edit/{id}")
    public String editUser(
            @PathVariable Integer userId,
            Model model) {
        UserDTO user = userService.findUserById(userId);
        model.addAttribute("user", user);
        return "edit";
    }

    @PostMapping("/edit/{userId}")
    public String updateUserById(
            @Valid @ModelAttribute("user") UserDTO updatedUserDTO,
            BindingResult result,
            @PathVariable Integer userId,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("user", updatedUserDTO);
            return "edit";
        }

        userService.editUser(updatedUserDTO, userID);
        return "redirect:/profile/index";
    }

    @GetMapping("/delete/{userId}")
    public String deleteUser(
            @RequestParam(name = "error", required = false) String error,
            @RequestParam(name = "success", required = false) String success,
            RedirectAttributes redirectAttributes,
            @PathVariable Integer userID,
            Principal principal,
            Model model) {

        String loggedInUsername = principal.getUsername();

        User loggedInUser = userService.findUserByEmail(loggedInUsername);

        if (loggedInUser != null && loggedInUser.getUserId().equals(userID)) {
            if (error != null) {
                redirectAttributes.addFlashAttribute("error", "You cannot delete yourself.");
            }
        } else {
            if (userService.doesUserExist(userID)) {
                userService.deleteUserById(userID);
                if (success != null) {
                    redirectAttributes.addFlashAttribute("success", "Xóa tài khoản thành công");
                }
            } else {
                if (error != null) {
                    redirectAttributes.addFlashAttribute("error", "Tài khoản không tồn tại");
                }
            }
        }
        return "redirect:/users";
    }
}