package org.spring.mockprojectwebapp.services.implement;

import org.spring.mockprojectwebapp.dtos.user.CommentNotificationDTO;
import org.spring.mockprojectwebapp.entities.Comment;
import org.spring.mockprojectwebapp.entities.Notification;
import org.spring.mockprojectwebapp.entities.User;
import org.spring.mockprojectwebapp.repositories.CommentRepository;
import org.spring.mockprojectwebapp.repositories.NotificationRepository;
import org.spring.mockprojectwebapp.repositories.UserRepository;
import org.spring.mockprojectwebapp.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<CommentNotificationDTO> getCommentNotificationsByUserId(int userId) {
        List<Notification> notifications = notificationRepository.findByUserUserIdOrderByCreatedAtDesc(userId);
        return notifications.stream()
                .filter(n -> n.getType() == Notification.NotificationType.COMMENT)
                .map(this::convertToCommentNotificationDTO)
                .collect(Collectors.toList());
    }

//    public void createCommentNotification(int userId, String content, int commentId) {
//        // Existing code
//        notification.setContent(content + " comment_" + commentId); // Store comment ID in content
//        // Rest of existing code
//    }
    public void createCommentNotification(int userId, String content, int commentId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setContent(content + " comment_" + commentId); // Store comment ID in content
        notification.setType(Notification.NotificationType.COMMENT);
        notification.setStatus(Notification.NotificationStatus.UNREAD);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);
    }

    public int getUnreadNotificationCount(int userId) {
        return notificationRepository.countUnreadNotifications(userId);
    }

    private CommentNotificationDTO convertToCommentNotificationDTO(Notification notification) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        CommentNotificationDTO dto = new CommentNotificationDTO();
        dto.setNotificationId(notification.getId());
        dto.setUserId(notification.getUser().getUserId());
        dto.setType(notification.getType().toString()); // Set the notification type

        // Extract comment ID from content if present
        Pattern pattern = Pattern.compile("comment_(\\d+)");
        Matcher matcher = pattern.matcher(notification.getContent());
        if (matcher.find()) {
            int commentId = Integer.parseInt(matcher.group(1));
            dto.setCommentId(commentId);

            // Fetch the comment content
            Comment comment = commentRepository.findById(commentId).orElse(null);
            if (comment != null) {
                dto.setCommentContent(comment.getContent());
            }

            // Remove the comment_ID part from the displayed content
            String displayContent = notification.getContent().replaceAll(" comment_\\d+", "");
            dto.setContent(displayContent);
        } else {
            dto.setContent(notification.getContent());
        }

        dto.setStatus(notification.getStatus().toString());
        dto.setCreatedAt(notification.getCreatedAt().format(formatter));

        return dto;
    }
}