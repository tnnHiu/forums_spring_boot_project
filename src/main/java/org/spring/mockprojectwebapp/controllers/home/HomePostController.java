package org.spring.mockprojectwebapp.controllers.home;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.spring.mockprojectwebapp.dtos.admin.CommentDTO;
import org.spring.mockprojectwebapp.dtos.admin.PostDTO;
import org.spring.mockprojectwebapp.dtos.user.UserCommentDTO;
import org.spring.mockprojectwebapp.entities.Comment;
import org.spring.mockprojectwebapp.services.CategoryService;
import org.spring.mockprojectwebapp.services.CommentService;
import org.spring.mockprojectwebapp.services.PostService;
import org.spring.mockprojectwebapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Controller
@RequestMapping("/")
public class HomePostController {

    @Autowired
    private PostService postService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;


    @GetMapping("/post/{id}")
    public String showPostDetail(@PathVariable("id") int id, Model model) {
        PostDTO postDTO = postService.findPostById(id);
        if (postDTO == null) {
            return "redirect:/error";
        }
        List<UserCommentDTO> userCommentDTOS = commentService.getCommentsByPostId(id);
        model.addAttribute("", postDTO);
        model.addAttribute("postDTO", postDTO);
        model.addAttribute("userCommentDTOS", userCommentDTOS);
        return "user/post-detail";
    }


    @GetMapping("/post/create")
    public String showCreatePostPage(Model model) {
        model.addAttribute("postDTO", new PostDTO());
        model.addAttribute("categoryDTOs", categoryService.getAllCategories());
        return "user/create-post";
    }

    @PostMapping("/post/create")
    public String createPost(Model model, @Valid @ModelAttribute PostDTO postDTO, @RequestParam("imageFile") MultipartFile imageFile, @RequestParam("categoryId") int categoryId, HttpSession session) {
        String imageUrl = saveImageFile(imageFile);
        if (imageUrl == null || imageUrl.isEmpty()) {
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi tải ảnh lên.");
            model.addAttribute("postDTO", postDTO);
            model.addAttribute("categories", categoryService.getAllCategories());
            return "user/create-post";
        }


        postDTO.setImageUrl(imageUrl);
        postDTO.setCategoryId(categoryId);
        int userId = (int) session.getAttribute("userId");
        postDTO.setUserId(userId);
        postDTO.setStatus(1);


        postService.savePost(postDTO);
        return "redirect:/";
    }

    @Autowired
    private ResourceLoader resourceLoader;

    private String saveImageFile(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }
        try {
            Resource resource = resourceLoader.getResource("classpath:static/img");
            File uploadDir = resource.getFile();

            if (!uploadDir.exists()) {
                if (!uploadDir.mkdirs()) {
                    throw new IOException("Không thể tạo thư mục: " + uploadDir.getAbsolutePath());
                }
            }

            String fileName = file.getOriginalFilename();
            String uniqueFileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + "_" + fileName;

            File dest = new File(uploadDir, uniqueFileName);
            file.transferTo(dest);

            return "/img/" + uniqueFileName;
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi lưu file: " + e.getMessage(), e);
        }
    }
}





