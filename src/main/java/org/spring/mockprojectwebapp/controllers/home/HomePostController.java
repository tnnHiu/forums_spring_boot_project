package org.spring.mockprojectwebapp.controllers.home;

import org.spring.mockprojectwebapp.dtos.PostDTO;
import org.spring.mockprojectwebapp.services.CategoryService;
import org.spring.mockprojectwebapp.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class HomePostController {

    @Autowired
    private PostService postService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/post/{id}")
    public String showPostDetail(@PathVariable("id") int id, Model model) {
        Optional<PostDTO> postDTO = postService.findPostById(id);
        if (postDTO.isPresent()) {
            model.addAttribute("postDTO", postDTO.get());
            return "post-detail";
        } else {
            return "redirect:/error";
        }
    }

    @GetMapping("/post/create")
    public String showCreatePostPage(Model model) {
        model.addAttribute("postDTO", new PostDTO());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "user/create-post";
    }

//    @PostMapping("/post/create")
//    public String createPost(
//            @RequestParam("title") String title,
//            @RequestParam("description") String description,
//            @RequestParam("content") String content,
//            @RequestParam("categoryId") int categoryId,
//            @RequestParam("imageFile") MultipartFile imageFile) {
//        String imageUrl = saveImageFile(imageFile);
//        PostDTO postDTO = new PostDTO();
//        postDTO.setTitle(title);
//        postDTO.setDescription(description);
//        postDTO.setContent(content);
//        postDTO.setCategoryId(categoryId);
//        postDTO.setImageUrl(imageUrl);
//        postDTO.setCreatedAt(LocalDateTime.now());
//        postDTO.setUpdatedAt(LocalDateTime.now());
//        postDTO.setStatus(1);
//        postService.savePost(postDTO);
//        return "redirect:/";
//    }
//
//    private String saveImageFile(MultipartFile file) {
//        try {
//            // Kiểm tra file có rỗng không
//            if (file.isEmpty()) {
//                throw new IllegalArgumentException("File upload trống!");
//            }
//
//            String originalFilename = file.getOriginalFilename();
//            if (originalFilename == null || !originalFilename.contains(".")) {
//                throw new IllegalArgumentException("Tên file không hợp lệ!");
//            }
//
//            // Trích xuất phần mở rộng một cách an toàn
//            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
//
//            // Đường dẫn thư mục upload
//            String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/uploads";
//            File directory = new File(uploadDir);
//            if (!directory.exists()) {
//                directory.mkdirs();
//            }
//
//            // Tạo tên file duy nhất
//            String uniqueFilename = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + fileExtension;
//            File uploadFile = new File(directory, uniqueFilename);
//            file.transferTo(uploadFile);
//
//            return "/uploads/" + uniqueFilename;
//        } catch (IOException e) {clea
//            e.printStackTrace();
//            return null;
//        } catch (IllegalArgumentException e) {
//            System.out.println("Lỗi upload file: " + e.getMessage());
//            return null;
//        }
//    }

}
