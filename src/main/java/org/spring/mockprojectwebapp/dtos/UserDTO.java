package org.spring.mockprojectwebapp.dtos;

import lombok.*;
import org.spring.mockprojectwebapp.entities.User.Status;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
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
