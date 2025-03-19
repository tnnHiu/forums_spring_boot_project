package org.spring.mockprojectwebapp.dtos.admin;

import lombok.*;
import org.spring.mockprojectwebapp.entities.Comment.Status;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private int id;
    private int postId;
    private int userId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Status status;
}
