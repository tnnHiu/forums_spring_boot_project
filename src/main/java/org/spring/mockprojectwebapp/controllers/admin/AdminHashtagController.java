package org.spring.mockprojectwebapp.controllers.admin;

import org.spring.mockprojectwebapp.dtos.admin.HashtagDTO;
import org.spring.mockprojectwebapp.services.HashtagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminHashtagController {

    @Autowired
    private HashtagService hashtagService;

    @GetMapping("/hashtags")
    public String listHashtags(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(required = false) String keyword,
                               Model model) {
        Page<HashtagDTO> hashtags;
        int size = 10; // Default page size
        if (keyword != null && !keyword.isEmpty()) {
            hashtags = hashtagService.searchHashtags(keyword, page, size);
        } else {
            hashtags = hashtagService.getAllHashtags(page, size);
        }
        model.addAttribute("hashtagDTOs", hashtags.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", hashtags.getTotalPages());
        model.addAttribute("keyword", keyword);
        return "admin/hashtag/index";
    }

    @PostMapping("/hashtags/add")
    public String addHashtag(@ModelAttribute HashtagDTO hashtagDTO, Model model, RedirectAttributes redirectAttributes) {
        try {
            hashtagService.saveHashtag(hashtagDTO);
            return "redirect:/admin/hashtags";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/hashtags";
        }
    }

    @PostMapping("/hashtags/update/{id}")
    public String updateHashtag(@PathVariable int id, @ModelAttribute HashtagDTO hashtagDTO, RedirectAttributes redirectAttributes) {
        try {
            hashtagDTO.setHashtagId(id);
            hashtagService.saveHashtag(hashtagDTO);
            return "redirect:/admin/hashtags";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin/hashtags";
        }
    }

    @PostMapping("/hashtags/delete/{id}")
    public String deleteHashtag(@PathVariable int id) {
        hashtagService.deleteHashtag(id);
        return "redirect:/admin/hashtags";
    }
}