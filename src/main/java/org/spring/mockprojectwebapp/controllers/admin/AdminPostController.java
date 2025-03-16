package org.spring.mockprojectwebapp.controllers.admin;

import org.spring.mockprojectwebapp.dtos.PostDTO;
import org.spring.mockprojectwebapp.services.implement.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminPostController {

    private final PostServiceImpl postServiceImpl;

    @Autowired
    public AdminPostController(PostServiceImpl postServiceImpl) {
        this.postServiceImpl = postServiceImpl;
    }

    @GetMapping("/posts")
    public String listPosts(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "8") int size,
            Model model) {

        Page<PostDTO> postPage;
        if (keyword != null && !keyword.trim().isEmpty()) {
            postPage = postServiceImpl.searchPosts(keyword, page, size);
        } else {
            postPage = postServiceImpl.getAllPosts(page, size);
        }

        model.addAttribute("posts", postPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postPage.getTotalPages());
        model.addAttribute("keyword", keyword);
        return "admin/post/index";
    }

    @GetMapping("/posts/{id}")
    public String viewPost(@PathVariable Integer id, Model model) {
        PostDTO post = postServiceImpl.getPostById(id);
        model.addAttribute("post", post);
        return "admin/post/view";
    }

    @GetMapping("/posts/{id}/edit")
    public String editPostForm(@PathVariable Integer id, Model model) {
        PostDTO post = postServiceImpl.getPostById(id);
        model.addAttribute("post", post);
        return "admin/post/edit";
    }

    @PostMapping("/posts/{id}")
    public String updatePost(@PathVariable Integer id, @ModelAttribute PostDTO postDTO) {
        postDTO.setId(id);
        postServiceImpl.updatePost(postDTO);
        return "redirect:/admin/posts";
    }

    @PostMapping("/posts/{id}/delete")
    public String deletePost(@PathVariable Integer id) {
        postServiceImpl.deletePost(id);
        return "redirect:/admin/posts";
    }
}