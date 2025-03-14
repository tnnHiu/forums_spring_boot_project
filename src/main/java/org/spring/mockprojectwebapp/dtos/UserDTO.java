package org.spring.mockprojectwebapp.dtos;

import lombok.*;
import org.spring.mockprojectwebapp.entities.User;

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
