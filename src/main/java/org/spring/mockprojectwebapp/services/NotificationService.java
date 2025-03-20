package org.spring.mockprojectwebapp.services;

import org.spring.mockprojectwebapp.dtos.user.CommentNotificationDTO;

import java.util.List;

public interface NotificationService {
    List<CommentNotificationDTO> getCommentNotificationsByUserId(int userId);
//    void createCommentNotification(int userId, String comment);
    void createCommentNotification(int userId, String comment, int commentId);
    int getUnreadNotificationCount(int userId);
}
