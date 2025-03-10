package org.spring.mockprojectwebapp.controllers.admin;

import jakarta.servlet.http.HttpServletRequest;
import org.spring.mockprojectwebapp.entities.Category;
import org.spring.mockprojectwebapp.services.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;



@Controller
@RequestMapping("/admin")
public class AdminCategoryController {

    private final CategoryServiceImpl categoryServiceImpl;

    @Autowired
    public AdminCategoryController(CategoryServiceImpl categoryServiceImpl) {
        this.categoryServiceImpl = categoryServiceImpl;
    }


    @GetMapping("/categories")
    public String showCategoriesPage(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "8") int size,
            Model model,
            HttpServletRequest request) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categoryPage = categoryServiceImpl.getCategories(keyword, pageable);

        model.addAttribute("categories", categoryPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", categoryPage.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("title", "Quản lý danh mục");
        model.addAttribute("currentUri", request.getRequestURI());

        // Thêm một đối tượng category rỗng để tránh Thymeleaf bị lỗi
        model.addAttribute("category", new Category());

        return "admin/category/index";
    }


    @PostMapping("/categories/add")
    public String addCategory(@ModelAttribute("category") Category category, Model model) {
        try {
            categoryServiceImpl.save(category);
            model.addAttribute("successMessage", "Thêm danh mục thành công!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi thêm danh mục.");
        }
        return "redirect:/admin/categories";
    }


    @GetMapping("/categories/edit/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable("id") int id) {
        Category category = categoryServiceImpl.findById(id);
        if (category == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
        }
        return ResponseEntity.ok(category);
    }



    @PostMapping("/categories/update/{id}")
    public String updateCategory(@PathVariable("id") int id, @ModelAttribute Category category) {
        Category existingCategory = categoryServiceImpl.findById(id);
        if (existingCategory != null) {
            existingCategory.setCategoryName(category.getCategoryName());
            existingCategory.setDescription(category.getDescription());
            categoryServiceImpl.update(id, existingCategory);
        }
        return "redirect:/admin/categories";
    }



    // Xóa danh mục
    @PostMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable("id") int id) {
        categoryServiceImpl.deleteById(id);
        return "redirect:/admin/categories";
    }


}