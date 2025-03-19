package org.spring.mockprojectwebapp.dtos.admin;

import lombok.*;
import org.spring.mockprojectwebapp.entities.User.Status;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private int userId;
    private String username;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Status status;
    private int roleId;
}
