package org.spring.mockprojectwebapp.controllers.admin;

import org.spring.mockprojectwebapp.dtos.HashtagDTO;
import org.spring.mockprojectwebapp.services.HashtagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("hashtags", hashtags.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", hashtags.getTotalPages());
        model.addAttribute("keyword", keyword);
        return "admin/hashtag/index";
    }

    @PostMapping("/hashtags/add")
    public String addHashtag(@ModelAttribute HashtagDTO hashtagDTO) {
        hashtagService.saveHashtag(hashtagDTO);
        return "redirect:/admin/hashtags";
    }

    @PostMapping("/hashtags/update/{id}")
    public String updateHashtag(@PathVariable int id, @ModelAttribute HashtagDTO hashtagDTO) {
        hashtagDTO.setId(id);
        hashtagService.saveHashtag(hashtagDTO);
        return "redirect:/admin/hashtags";
    }

    @PostMapping("/hashtags/delete/{id}")
    public String deleteHashtag(@PathVariable int id) {
        hashtagService.deleteHashtag(id);
        return "redirect:/admin/hashtags";
    }
}