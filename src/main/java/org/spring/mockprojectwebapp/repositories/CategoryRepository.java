package org.spring.mockprojectwebapp.repositories;

import org.spring.mockprojectwebapp.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("SELECT c FROM Category c WHERE c.categoryName LIKE %:keyword% OR c.description LIKE %:keyword%")
    Page<Category> findByCategoryNameContainingIgnoreCase(String keyword, Pageable pageable);
}