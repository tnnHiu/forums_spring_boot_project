package org.spring.mockprojectwebapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReportController {
    @GetMapping("/report")
    public String showCategory() {
        return "admin/report/report";
    }
}
