package org.spring.mockprojectwebapp.controllers.admin;

import org.spring.mockprojectwebapp.dtos.UserDTO;
import org.spring.mockprojectwebapp.entities.User;
import org.spring.mockprojectwebapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminAccountController {

    @Autowired
    private UserService userService;


//    @GetMapping()
//    public String users(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
//        List<UserDTO> users;
//        if (keyword != null && !keyword.trim().isEmpty()) {
//            users = userService.searchUsers(keyword);
//        } else {
//            users = userService.findAllUsers();
//        }
//        model.addAttribute("users", users);
//        model.addAttribute("keyword", keyword);
//        return "admin/account/index";
//    }


    @GetMapping("/accounts")
    public String users(Model model) {
        List<UserDTO> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "admin/account/index";
    }

    @PostMapping("/accounts/delete/{userId}")
    public String deleteUser(@PathVariable Integer userId, RedirectAttributes redirectAttributes) {
        if (userService.doesUserExist(userId)) {
            userService.deleteUserById(userId);
            redirectAttributes.addFlashAttribute("success", "User has been deleted successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "User does not exist.");
        }
        return "admin/account/index";
    }

    @PostMapping("/accounts/status/{userId}")
    public String updateUserStatus(@PathVariable Integer userId, @RequestParam("status") User.Status status, RedirectAttributes redirectAttributes) {
        userService.updateUserStatus(userId, status);
        redirectAttributes.addFlashAttribute("success", "User status updated successfully!");
        return "redirect:/admin/accounts";
    }
}
