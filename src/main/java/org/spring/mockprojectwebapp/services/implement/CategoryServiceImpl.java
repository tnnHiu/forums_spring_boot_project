package org.spring.mockprojectwebapp.services.implement;

import org.spring.mockprojectwebapp.entities.Category;
import org.spring.mockprojectwebapp.repositories.CategoryRepository;
import org.spring.mockprojectwebapp.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Iterable<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Integer id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

    @Override
    public Category save(Category category) {
        category.setCreatedAt(category.getCreatedAt() == null ? java.time.LocalDateTime.now() : category.getCreatedAt());
        category.setUpdatedAt(java.time.LocalDateTime.now());
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Integer id, Category updatedCategory) {
        Category existingCategory = findById(id);
        existingCategory.setCategoryName(updatedCategory.getCategoryName());
        existingCategory.setDescription(updatedCategory.getDescription());
        existingCategory.setUpdatedAt(java.time.LocalDateTime.now());
        return categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteById(Integer id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }

    public Page<Category> getCategories(String keyword, Pageable pageable) {
        if (keyword == null || keyword.isBlank()) {
            return categoryRepository.findAll(pageable);
        }
        return categoryRepository.findByCategoryNameContainingIgnoreCase(keyword, pageable);
    }
}