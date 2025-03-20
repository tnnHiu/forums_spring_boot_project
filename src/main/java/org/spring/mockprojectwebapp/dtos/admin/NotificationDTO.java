package org.spring.mockprojectwebapp.dtos.admin;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
    private int notificationId;
    private int userId;
    private String userName;
    private String content;
    private String type;
    private int status;
    private LocalDateTime createdAt;
}
