package org.spring.mockprojectwebapp.services;

import org.spring.mockprojectwebapp.dtos.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    CategoryDTO findById(Integer id);

    CategoryDTO save(CategoryDTO categoryDTO);

    CategoryDTO update(Integer id, CategoryDTO categoryDTO);

    void deleteById(Integer id);

    Page<CategoryDTO> getCategories(String keyword, Pageable pageable);
}