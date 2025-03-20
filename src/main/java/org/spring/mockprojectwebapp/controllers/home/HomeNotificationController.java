package org.spring.mockprojectwebapp.controllers.home;

import org.slf4j.Logger;
import org.spring.mockprojectwebapp.dtos.user.CommentNotificationDTO;
import org.spring.mockprojectwebapp.entities.Comment;
import org.spring.mockprojectwebapp.entities.Notification;
import org.spring.mockprojectwebapp.repositories.CommentRepository;
import org.spring.mockprojectwebapp.repositories.NotificationRepository;
import org.spring.mockprojectwebapp.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class HomeNotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @GetMapping("/notification")
    public String showNotifications(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        List<CommentNotificationDTO> notifications = notificationService.getCommentNotificationsByUserId(userId);
        model.addAttribute("notifications", notifications);

        return "user/notification";
    }

    @GetMapping("/notification/{notificationId}")
    public String viewPostFromNotification(@PathVariable("notificationId") int notificationId) {
        // Find notification by ID
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        // Mark notification as read
        notification.setStatus(Notification.NotificationStatus.READ);
        notificationRepository.save(notification);

        // Extract the comment ID from the notification content using regex
        Pattern pattern = Pattern.compile("comment_(\\d+)");
        Matcher matcher = pattern.matcher(notification.getContent());

        if (matcher.find()) {
            int commentId = Integer.parseInt(matcher.group(1));

            // Find comment by ID
            Comment comment = commentRepository.findById(commentId)
                    .orElseThrow(() -> new RuntimeException("Comment not found"));

            // Get post ID and redirect to the post page
            int postId = comment.getPost().getId();
            return "redirect:/post/" + postId;
        }

        // Fallback if comment ID is not found in notification
        return "redirect:/notification";
    }

    @GetMapping("/api/notification/count")
    @ResponseBody
    public int getUnreadCount(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return 0;
        }
        return notificationService.getUnreadNotificationCount(userId);
    }
}