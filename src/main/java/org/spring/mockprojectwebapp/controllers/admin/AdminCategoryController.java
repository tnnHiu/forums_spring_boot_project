package org.spring.mockprojectwebapp.controllers.admin;

import jakarta.servlet.http.HttpServletRequest;
import org.spring.mockprojectwebapp.dtos.admin.CategoryDTO;
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


@Controller
@RequestMapping("/admin")
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public String showCategoriesPage(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "8") int size,
            Model model,
            HttpServletRequest request) {

        Pageable pageable = PageRequest.of(page, size);
        Page<CategoryDTO> categoryDTOPage = categoryService.getCategories(keyword, pageable);

        model.addAttribute("categoryDTOs", categoryDTOPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", categoryDTOPage.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("title", "Quản lý danh mục");
        model.addAttribute("currentUri", request.getRequestURI());

        model.addAttribute("categoryDTO", new CategoryDTO());

        return "admin/category/index";
    }

    @PostMapping("/categories/add")
    public String addCategory(
            @ModelAttribute("categoryDTO") CategoryDTO categoryDTO,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Dữ liệu không hợp lệ. Vui lòng kiểm tra lại.");
            return "admin/category/index";
        }

        try {
            categoryService.save(categoryDTO);
            model.addAttribute("successMessage", "Thêm danh mục thành công!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi thêm danh mục: " + e.getMessage());
        }

        return "redirect:/admin/categories";
    }

    @GetMapping("/categories/edit/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable("id") int id) {
        CategoryDTO categoryDTO = categoryService.findById(id);
        if (categoryDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Danh mục không tồn tại.");
        }
        return ResponseEntity.ok(categoryDTO);
    }

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

        try {
            categoryService.update(id, categoryDTO);
            model.addAttribute("successMessage", "Cập nhật danh mục thành công!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi cập nhật danh mục: " + e.getMessage());
        }

        return "redirect:/admin/categories";
    }

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