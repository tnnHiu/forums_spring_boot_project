package org.spring.mockprojectwebapp.controllers.admin;

import jakarta.servlet.http.HttpServletRequest;
import org.spring.mockprojectwebapp.dtos.UserDTO;
import org.spring.mockprojectwebapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminAccountController {

    @Autowired
    private UserService userService;

//    @GetMapping("/accounts")
//    public String showAccountsPage(
//            @RequestParam(value = "keyword", required = false) String keyword,
//            @RequestParam(value = "page", defaultValue = "0") int page,
//            @RequestParam(value = "size", defaultValue = "8") int size,
//            Model model,
//            HttpServletRequest request) {
//
//        Pageable pageable = PageRequest.of(page, size);
//        Page<UserDTO> userDTOPage = userService.getUsers(keyword, pageable);
//
//        model.addAttribute("userDTOs", userDTOPage.getContent());
//        model.addAttribute("currentPage", page);
//        model.addAttribute("totalPages", userDTOPage.getTotalPages());
//        model.addAttribute("keyword", keyword);
//        model.addAttribute("title", "Account Management");
//        model.addAttribute("currentUri", request.getRequestURI());
//
//        return "admin/account/index";
//    }
}