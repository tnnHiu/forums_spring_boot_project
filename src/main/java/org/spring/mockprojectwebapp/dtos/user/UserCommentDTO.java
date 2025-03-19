package org.spring.mockprojectwebapp.dtos.user;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class UserCommentDTO {
    private int commentId;
    private int userId;
    private String avatar;
    private String userName;
    private int postId;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String commentStatus;
}
