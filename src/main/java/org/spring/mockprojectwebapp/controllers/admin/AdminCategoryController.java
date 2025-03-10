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
    public String showCategoriesPage(@RequestParam(value = "keyword", required = false) String keyword, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "8") int size, Model model, HttpServletRequest request) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categoryPage = categoryServiceImpl.getCategories(keyword, pageable);

        model.addAttribute("categories", categoryPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", categoryPage.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("title", "Quản lý danh mục");
        model.addAttribute("currentUri", request.getRequestURI());

        return "admin/category/index";
    }


    @PostMapping("/category/add")
    @ResponseBody
    public ResponseEntity<?> addCategory(@RequestBody Category category) {
        try {
            categoryServiceImpl.save(category);
            return ResponseEntity.ok(category);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Có lỗi xảy ra khi thêm danh mục.");
        }
    }


    @GetMapping("/categories/edit/{id}")
    @ResponseBody
    public ResponseEntity<?> getCategoryById(@PathVariable("id") int id) {
        try {
            Category category = categoryServiceImpl.findById(id);
            if (category == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy danh mục.");
            }
            return ResponseEntity.ok(category);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi xảy ra trong quá trình xử lý.");
        }
    }

    // Xử lý cập nhật danh mục
//    @PostMapping("/categories/update/{id}")
//    public String updateCategory(
//            @PathVariable int id,
//            @ModelAttribute("category") Category category,
//            RedirectAttributes redirectAttributes) {
//        try {
//            categoryServiceImpl.update(id, category);
//            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật danh mục thành công!");
//            return "redirect:/admin/categories";
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi cập nhật danh mục.");
//            return "redirect:/admin/categories/edit/" + id;
//        }
//    }

    @PostMapping("/categories/update/{id}")
    @ResponseBody
    public ResponseEntity<?> updateCategory(@PathVariable int id, @RequestBody Category category) {
        try {
            Category existingCategory = categoryServiceImpl.findById(id);
            if (existingCategory == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy danh mục với ID: " + id);
            }
            existingCategory.setCategoryName(category.getCategoryName());
            existingCategory.setDescription(category.getDescription());
            categoryServiceImpl.update(id, existingCategory);
            return ResponseEntity.ok("Cập nhật danh mục thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi xảy ra khi cập nhật danh mục.");
        }
    }


    // Xóa danh mục
    @DeleteMapping("/categories/delete/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteCategory(@PathVariable("id") int id) {
        try {
            categoryServiceImpl.deleteById(id);
            return ResponseEntity.ok("Xóa danh mục thành công!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Có lỗi xảy ra khi xóa danh mục.");
        }
    }

}