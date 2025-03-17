package org.spring.mockprojectwebapp.dtos;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.spring.mockprojectwebapp.entities.Category;
import org.spring.mockprojectwebapp.entities.Post;


import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private Integer id;
    private Integer isPremium;
    private Integer categoryId;
    private Integer userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String title;
    private String content;
    private String statusName;
    private String categoryName;
    private String userName;
    private Post.Status status;
    private Category category;
    private String imageUrl;
    private String description;
    private String author;
}