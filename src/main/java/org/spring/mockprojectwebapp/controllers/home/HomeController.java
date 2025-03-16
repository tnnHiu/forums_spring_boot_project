package org.spring.mockprojectwebapp.controllers.home;

import jakarta.servlet.http.HttpSession;
import org.spring.mockprojectwebapp.dtos.PostDTO;
import org.spring.mockprojectwebapp.dtos.UserDTO;
import org.spring.mockprojectwebapp.services.PostService;
import org.spring.mockprojectwebapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String showHomePage(Model model, HttpSession session) {
        List<PostDTO> recentPostDTOs = postService.getRecentPosts();
        List<UserDTO> receUserDTOS = userService.findAllUsers();
        model.addAttribute("recentPostDTOs", recentPostDTOs);
        model.addAttribute("receUserDTOS", receUserDTOS);
        model.addAttribute("userName", session.getAttribute("userName"));
        return "index";
    }


}