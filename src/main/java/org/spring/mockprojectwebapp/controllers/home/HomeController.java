package org.spring.mockprojectwebapp.controllers.home;

import org.spring.mockprojectwebapp.dtos.PostDTO;
import org.spring.mockprojectwebapp.services.PostService;
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

    @GetMapping
    public String showHomePage(Model model) {
        List<PostDTO> recentPostDTOs = postService.getRecentPosts();
        model.addAttribute("recentPostDTOs", recentPostDTOs);
        return "index";
    }


}