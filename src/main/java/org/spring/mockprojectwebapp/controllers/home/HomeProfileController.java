package org.spring.mockprojectwebapp.controllers.home;


import jakarta.servlet.http.HttpSession;
import org.spring.mockprojectwebapp.dtos.admin.PostDTO;
import org.spring.mockprojectwebapp.dtos.admin.UserDTO;
import org.spring.mockprojectwebapp.services.PostService;
import org.spring.mockprojectwebapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/profile")
public class HomeProfileController {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    @RequestMapping
    public String showProfilePage(HttpSession session, Model model) {
        int userId = (int) session.getAttribute("userId");
        UserDTO userDTO = userService.findUserById(userId);
        List<PostDTO> postDTOs = postService.getUserPosts(userId);
        model.addAttribute("userDTO", userDTO);
        model.addAttribute("postDTOs", postDTOs);
        return "user/profile";
    }
}
