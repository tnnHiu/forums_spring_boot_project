package org.spring.mockprojectwebapp.dtos;

import lombok.*;
import org.spring.mockprojectwebapp.entities.User;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private int userId;
    private String username;
    private String email;
    private User.Status status;
}
