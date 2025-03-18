package org.spring.mockprojectwebapp.dtos.admin;

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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int status;
    private boolean isPremium;
    private String imagePath;
    private String categoryName;
    private String username;
    private int userId;
    private int categoryId;
}
