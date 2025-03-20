package org.spring.mockprojectwebapp.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostNotificationDTO {
    private int id;
    private int postId;// Assuming you want to display post title
    private int userId;
    private String username; // Assuming you want to display username
    private String content;
    private String status; // "UNREAD" or "READ"
    private LocalDateTime createdAt;
}