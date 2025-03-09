package org.spring.mockprojectwebapp.services;

import org.spring.mockprojectwebapp.entities.Category;

public interface CategoryService {

    Iterable<Category> findAll();

    Category findById(Integer id);

    Category save(Category category);

    Category update(Integer id, Category category);

    void deleteById(Integer id);
}
