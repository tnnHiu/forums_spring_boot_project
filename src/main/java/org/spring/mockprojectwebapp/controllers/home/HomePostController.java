package org.spring.mockprojectwebapp.controllers.home;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.spring.mockprojectwebapp.dtos.PostDTO;
import org.spring.mockprojectwebapp.services.CategoryService;
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


@Controller
@RequestMapping("/")
public class HomePostController {

    @Autowired
    private PostService postService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;

    @GetMapping("/post/{id}")
    public String showPostDetail(@PathVariable("id") int id, Model model) {
        PostDTO postDTO = postService.findPostById(id);
        if (postDTO == null) {
            return "redirect:/error";
        }
        model.addAttribute("postDTO", postDTO);
        return "user/post-detail";
    }

    @GetMapping("/post/create")
    public String showCreatePostPage(Model model) {
        model.addAttribute("postDTO", new PostDTO());
        model.addAttribute("categoryDTOs", categoryService.getAllCategories());
        return "user/create-post";
    }

    @PostMapping("/post/create")
    public String createPost(
            Model model,
            @Valid @ModelAttribute PostDTO postDTO,
            @RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam("categoryId") int categoryId,
            HttpSession session) {
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


//    private String saveImageFile(MultipartFile file) {
//        if  (file.isEmpty()) {
//            return null;
//        }
//
//        try {
//
//            File uploadDir = new File("D:\\Source Java\\v7\\forums_spring_boot_project\\src\\main\\resources\\static\\uploads");
//
//            // Tạo thư mục nếu nó không tồn tại
//            if (!uploadDir.exists()) {
//                if (!uploadDir.mkdirs()) {
//                    throw new IOException("Không thể tạo thư mục: " + uploadDir.getAbsolutePath());
//                }
//            }
//
//            // Tạo tên file duy nhất
//            String fileName = file.getOriginalFilename();
//            String uniqueFileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + "_" + fileName;
//
//            // Lưu file
//            File dest = new File(uploadDir, uniqueFileName);
//            file.transferTo(dest);
//
//            // Trả về đường dẫn tương đối để hiển thị trên frontend
//            return "/uploads/" + uniqueFileName;
//        } catch (IOException e) {
//            throw new RuntimeException("Lỗi khi lưu file: " + e.getMessage(), e);
//        } }


}





