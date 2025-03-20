package org.spring.mockprojectwebapp.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentNotificationDTO {
    private int commentId;
    private int notificationId;
    private int userId;
    private String content;
    private String status;
    private String createdAt;
    private String commentStatus;
    private String commentContent;
    private String type;
}
