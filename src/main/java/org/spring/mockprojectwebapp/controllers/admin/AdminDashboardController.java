package org.spring.mockprojectwebapp.controllers.admin;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    @GetMapping
    public String showDashboard(HttpServletRequest request, Model model) {
        model.addAttribute("title", "Admin Dashboard");
        model.addAttribute("currentUri", request.getRequestURI());
        return "admin/dashboard/index";
    }
}
