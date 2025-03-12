package org.spring.mockprojectwebapp.controllers.admin;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminPostController {

    @GetMapping("/posts")
    public String showPost(HttpServletRequest request, Model model) {
        model.addAttribute("title", "Post Management");
        model.addAttribute("currentUri", request.getRequestURI());
        return "admin/post/index";
    }
}
