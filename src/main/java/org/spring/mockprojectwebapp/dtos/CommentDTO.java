package org.spring.mockprojectwebapp.dtos;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private int id;
    private int postId;
    private String username;
    private String content;
    private LocalDateTime createdAt;
}
