package org.spring.mockprojectwebapp.controllers.admin;

import jakarta.servlet.http.HttpServletRequest;
import org.spring.mockprojectwebapp.dtos.admin.PostDTO;
import org.spring.mockprojectwebapp.services.PostService;
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
public class AdminPostController {

    @Autowired
    private PostService postService;

    @GetMapping("/posts")
    public String showPostsPage(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            HttpServletRequest request,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<PostDTO> postDTOPage = postService.getPosts(keyword, pageable);

        model.addAttribute("postDTOs", postDTOPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postDTOPage.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("title", "Post Management");
        model.addAttribute("currentUri", request.getRequestURI());

        return "admin/post/index";
    }
}