package org.spring.mockprojectwebapp.controllers.home;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeProfileController {
    @GetMapping("/profile")
    public String showProfile() {
        return "/user/profile";
    }
}
