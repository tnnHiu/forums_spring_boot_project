package org.spring.mockprojectwebapp.controllers.admin;

import jakarta.servlet.http.HttpServletRequest;
import org.spring.mockprojectwebapp.dtos.PostDTO;
import org.spring.mockprojectwebapp.entities.Post;
import org.spring.mockprojectwebapp.services.CategoryService;
import org.spring.mockprojectwebapp.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminPostController {

    @Autowired
    private PostService postService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/posts")
    public String showPostsPage(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "categoryId", required = false) Integer categoryId,
            @RequestParam(value = "isPremium", required = false) Integer isPremium,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            HttpServletRequest request,
            Model model) {

        Page<PostDTO> postDTOPage = postService.filterPosts(keyword, status, categoryId, isPremium, page, size);

        model.addAttribute("postDTOs", postDTOPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postDTOPage.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("status", status);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("isPremium", isPremium);
        model.addAttribute("title", "Post Management");
        model.addAttribute("currentUri", request.getRequestURI());

        // Add categories for the dropdown
        model.addAttribute("categories", categoryService.getAllCategories());

        return "admin/post/index";
    }

    @PostMapping("/posts/{id}")
    public String updatePostStatus(@PathVariable int id,
                                   @RequestParam("status") int statusOrdinal,
                                   RedirectAttributes redirectAttributes) {
        try {
            // Convert status from int to Post.Status enum
            Post.Status status = Post.Status.values()[statusOrdinal];

            // Update the post status (the service already updates updatedAt field)
            postService.updatePostStatus(id, status);

            redirectAttributes.addFlashAttribute("successMessage", "Post status updated successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update post status: " + e.getMessage());
        }

        return "redirect:/admin/posts";
    }

    @PostMapping("/posts/delete/{id}")
    public String deletePost(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            postService.deletePost(id);
            redirectAttributes.addFlashAttribute("successMessage", "Post deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete post: " + e.getMessage());
        }

        return "redirect:/admin/posts";
    }
}