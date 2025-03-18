package org.spring.mockprojectwebapp.controllers.admin;

import jakarta.servlet.http.HttpServletRequest;
import org.spring.mockprojectwebapp.entities.User;
import org.spring.mockprojectwebapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminProfileController {

    @Autowired
    private UserService userService;

    @GetMapping("/accounts")
    public String showAccountsPage(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            Model model, HttpServletRequest request) {

        Pageable pageable = PageRequest.of(page, size);
        Page<User> accountPage = userService.getAccounts(keyword, pageable);

        model.addAttribute("accounts", accountPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", accountPage.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("title", "Quản lý tài khoản");
        model.addAttribute("currentUri", request.getRequestURI());

        return "admin/account/index";
    }


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
//        return "admin/profile/index";
//    }


//    @GetMapping("/account")
//    public String users(Model model) {
//        List<UserDTO> users = userService.findAllUsers();
//        model.addAttribute("users", users);
//        return "admin/profile/index";
//    }

    @PostMapping("/accounts/delete/{userId}")
    public String deleteUser(@PathVariable Integer userId, RedirectAttributes redirectAttributes) {
        if (userService.doesUserExist(userId)) {
            userService.deleteUserById(userId);
            redirectAttributes.addFlashAttribute("success", "User has been deleted successfully.");
        } else {
            redirectAttributes.addFlashAttribute("error", "User does not exist.");
        }
        return "admin/accounts";
    }

    @PostMapping("/accounts/status/{userId}")
    public String updateUserStatus(@PathVariable Integer userId, @RequestParam("status") User.Status status, RedirectAttributes redirectAttributes) {
        userService.updateUserStatus(userId, status);
        redirectAttributes.addFlashAttribute("success", "User status updated successfully!");
        return "/admin/accounts";
    }
}
