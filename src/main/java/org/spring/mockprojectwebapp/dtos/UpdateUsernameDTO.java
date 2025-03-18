package org.spring.mockprojectwebapp.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUsernameDTO {
    @NotEmpty(message = "Username cannot be empty")
    private String username;
}