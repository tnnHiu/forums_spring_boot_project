package org.spring.mockprojectwebapp.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private int postId;
    private String title;
    private String content;
    private String imageUrl;
    private String description;
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private CategoryDTO category;
}
