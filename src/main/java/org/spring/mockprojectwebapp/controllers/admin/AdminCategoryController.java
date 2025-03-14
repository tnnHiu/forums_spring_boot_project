package org.spring.mockprojectwebapp.controllers.admin;

import jakarta.servlet.http.HttpServletRequest;
import org.spring.mockprojectwebapp.dtos.CategoryDTO;
import org.spring.mockprojectwebapp.entities.Category;
import org.spring.mockprojectwebapp.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

<<<<<<< HEAD
<<<<<<< HEAD
import java.time.LocalDateTime;

@Controller
@RequestMapping("/admin")
public class AdminCategoryController {
=======
@Controller
@RequestMapping("/admin")
public class AdminCategoryController {
=======
@Controller
@RequestMapping("/admin")
public class AdminCategoryController {
>>>>>>> b8af64f984abfef6c0d0791aa1d9d922c04981e8
    private final CategoryServiceImpl categoryServiceImpl;
>>>>>>> b8af64f984abfef6c0d0791aa1d9d922c04981e8

    @Autowired
    public CategoryService categoryService;

    // Hiển thị trang danh sách danh mục
    @GetMapping("/categories")
    public String showCategoriesPage(@RequestParam(value = "keyword", required = false) String keyword, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "8") int size, Model model, HttpServletRequest request) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categoryPage = categoryService.getCategories(keyword, pageable);

        // Chuyển đổi từ Category sang CategoryDTO
        Page<CategoryDTO> categoryDTOPage = categoryPage.map(category -> CategoryDTO.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .description(category.getDescription())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build());
        model.addAttribute("categoryDTOs", categoryDTOPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", categoryDTOPage.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("title", "Quản lý danh mục");
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("categoryDTO", new CategoryDTO());
        return "admin/category/index";
    }

<<<<<<< HEAD
<<<<<<< HEAD
    // Thêm danh mục
=======
>>>>>>> b8af64f984abfef6c0d0791aa1d9d922c04981e8
=======
>>>>>>> b8af64f984abfef6c0d0791aa1d9d922c04981e8
    @PostMapping("/categories/add")
    public String addCategory(
            @ModelAttribute("categoryDTO") CategoryDTO categoryDTO,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Dữ liệu không hợp lệ. Vui lòng kiểm tra lại.");
            return "admin/category/index";
        }

        // Chuyển đổi từ CategoryDTO sang Category
        Category category = Category.builder()
                .categoryName(categoryDTO.getCategoryName())
                .description(categoryDTO.getDescription())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        try {
            categoryService.save(category);
            model.addAttribute("successMessage", "Thêm danh mục thành công!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi thêm danh mục: " + e.getMessage());
        }

        return "redirect:/admin/categories";
    }

<<<<<<< HEAD
<<<<<<< HEAD
    // Lấy thông tin danh mục để chỉnh sửa (API)
=======
>>>>>>> b8af64f984abfef6c0d0791aa1d9d922c04981e8
=======
>>>>>>> b8af64f984abfef6c0d0791aa1d9d922c04981e8
    @GetMapping("/categories/edit/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable("id") int id) {
        Category category = categoryService.findById(id);
        if (category == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Danh mục không tồn tại.");
        }

        // Chuyển đổi từ Category sang CategoryDTO
        CategoryDTO categoryDTO = CategoryDTO.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .description(category.getDescription())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();

        return ResponseEntity.ok(categoryDTO);
    }

<<<<<<< HEAD
<<<<<<< HEAD
    // Cập nhật danh mục
=======
>>>>>>> b8af64f984abfef6c0d0791aa1d9d922c04981e8
=======
>>>>>>> b8af64f984abfef6c0d0791aa1d9d922c04981e8
    @PostMapping("/categories/update/{id}")
    public String updateCategory(
            @PathVariable("id") int id,
            @ModelAttribute("categoryDTO") CategoryDTO categoryDTO,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Dữ liệu không hợp lệ. Vui lòng kiểm tra lại.");
            return "admin/category/index";
        }

        Category existingCategory = categoryService.findById(id);
        if (existingCategory == null) {
            model.addAttribute("errorMessage", "Danh mục không tồn tại.");
            return "redirect:/admin/categories";
        }

        // Cập nhật thông tin từ CategoryDTO
        existingCategory.setCategoryName(categoryDTO.getCategoryName());
        existingCategory.setDescription(categoryDTO.getDescription());
        existingCategory.setUpdatedAt(LocalDateTime.now());

        try {
            categoryService.save(existingCategory);
            model.addAttribute("successMessage", "Cập nhật danh mục thành công!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi cập nhật danh mục: " + e.getMessage());
        }

        return "redirect:/admin/categories";
    }

    // Xóa danh mục
    @PostMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable("id") int id, Model model) {
        try {
            categoryService.deleteById(id);
            model.addAttribute("successMessage", "Xóa danh mục thành công!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi xóa danh mục: " + e.getMessage());
        }
        return "redirect:/admin/categories";
    }
}