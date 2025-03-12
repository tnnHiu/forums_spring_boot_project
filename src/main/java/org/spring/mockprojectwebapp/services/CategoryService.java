package org.spring.mockprojectwebapp.services;

import org.spring.mockprojectwebapp.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


public interface CategoryService {

    Category findById(Integer id);

    Category save(Category category);

    Category update(Integer id, Category category);

    void deleteById(Integer id);

    Page<Category> getCategories(String keyword, Pageable pageable);
}