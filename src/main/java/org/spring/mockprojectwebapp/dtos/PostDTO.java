package org.spring.mockprojectwebapp.dtos;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.spring.mockprojectwebapp.entities.Post.Status;
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
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private CategoryDTO category;
}
