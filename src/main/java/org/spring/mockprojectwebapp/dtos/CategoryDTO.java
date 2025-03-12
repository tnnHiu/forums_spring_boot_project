package org.spring.mockprojectwebapp.dtos;

import lombok.*;
import org.spring.mockprojectwebapp.entities.Category;

import java.time.LocalDateTime;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryDTO {

    private int categoryId;
    private String categoryName;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CategoryDTO fromEntity(Category category) {
        if (category == null) {
            return null;
        }
        return CategoryDTO.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .description(category.getDescription())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }

    public Category toEntity() {
        return Category.builder()
                .categoryId(categoryId)
                .categoryName(categoryName)
                .description(description)
                .createdAt(createdAt != null ? createdAt : LocalDateTime.now())
                .updatedAt(updatedAt != null ? updatedAt : LocalDateTime.now())
                .build();
    }
}
