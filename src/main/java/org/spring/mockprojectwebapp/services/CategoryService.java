package org.spring.mockprojectwebapp.services;

import org.spring.mockprojectwebapp.dtos.admin.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    CategoryDTO findById(Integer id);
    CategoryDTO save(CategoryDTO categoryDTO);
    CategoryDTO update(Integer id, CategoryDTO categoryDTO);
    void deleteById(Integer id);
    Page<CategoryDTO> getCategories(String keyword, Pageable pageable);
    List<CategoryDTO> getAllCategories();
}