package org.spring.mockprojectwebapp.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private int userId;
    private String username;
    private String email;
    private String status;
}
