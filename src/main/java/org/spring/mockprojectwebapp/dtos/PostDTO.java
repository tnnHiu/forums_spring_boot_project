package org.spring.mockprojectwebapp.dtos;

import lombok.Getter;
import lombok.Setter;
import org.spring.mockprojectwebapp.entities.Category;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostDTO {
    private Integer id;
    private Integer isPremium;
    private Integer categoryId;
    private Integer status;
    private Integer userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String title;
    private String content;
    private String statusName;
    private String categoryName;
    private String userName;
    private Category category;
}