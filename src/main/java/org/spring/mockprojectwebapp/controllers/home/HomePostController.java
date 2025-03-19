package org.spring.mockprojectwebapp.controllers.home;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.spring.mockprojectwebapp.dtos.admin.PostDTO;
import org.spring.mockprojectwebapp.dtos.user.UserCommentDTO;
import org.spring.mockprojectwebapp.services.CategoryService;
import org.spring.mockprojectwebapp.services.CommentService;
import org.spring.mockprojectwebapp.services.PostService;
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
    private CommentService commentService;

    @Autowired
    private ResourceLoader resourceLoader;

    @GetMapping("/post/{id}")
    public String showPostDetail(@PathVariable("id") int id, Model model) {
        PostDTO postDTO = postService.findPostById(id);
        if (postDTO == null) {
            return "redirect:/error";
        }

        List<UserCommentDTO> firstThreeComments = commentService.getCommentsByPostId(id).stream().limit(3).toList();

        int totalComments = commentService.getCommentsByPostId(id).size();

        model.addAttribute("postDTO", postDTO);
        model.addAttribute("userCommentDTOs", firstThreeComments);
        model.addAttribute("totalComments", totalComments);

        return "user/post-detail";
    }

    @GetMapping("/post/{postId}/load-more-comments")
    public String loadMoreComments(@PathVariable("postId") int postId, @RequestParam("offset") int offset, Model model) {
        List<UserCommentDTO> allComments = commentService.getCommentsByPostId(postId);
        List<UserCommentDTO> moreComments = allComments.stream().skip(offset).limit(3).toList();
        model.addAttribute("userCommentDTOs", moreComments);
        model.addAttribute("postId", postId);
        return "fragments/post-comment :: commentList";
    }

    @PostMapping("/post/create-comment")
    public String createComment(@RequestParam("postId") int postId,
                                @RequestParam("comment") String commentContent, HttpSession session, Model model) {

        int userId = (int) session.getAttribute("userId");

        UserCommentDTO userCommentDTO = UserCommentDTO.builder().postId(postId).userId(userId).comment(commentContent).createdAt(LocalDateTime.now()).commentStatus("ACTIVE").build();

        commentService.saveComment(userCommentDTO);

        List<UserCommentDTO> userCommentDTOs = commentService.getCommentsByPostId(postId);
        model.addAttribute("userCommentDTOs", userCommentDTOs);
        model.addAttribute("postId", postId);
        return "fragments/post-comment :: commentList";
    }

    @GetMapping("/new-post")
    public String showCreatePostPage(Model model) {
        model.addAttribute("postDTO", new PostDTO());
        model.addAttribute("categoryDTOs", categoryService.getAllCategories());
        return "user/create-post";
    }

    @PostMapping("/new-post")
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
        Integer userId = (Integer) session.getAttribute("userId");

        postDTO.setUserId(userId);
        postDTO.setStatus(1);

        postService.savePost(postDTO);
        return "redirect:/";
    }

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