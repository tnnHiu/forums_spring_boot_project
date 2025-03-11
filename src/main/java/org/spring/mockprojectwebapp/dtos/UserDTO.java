package org.spring.mockprojectwebapp.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
}