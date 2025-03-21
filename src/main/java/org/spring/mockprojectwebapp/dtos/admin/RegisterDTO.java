package org.spring.mockprojectwebapp.dtos.admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {

    @NotEmpty
    private String username;

    @Email
    @NotEmpty
    private String email;

    @Size(min = 8, max = 20, message = "Độ dài mật khẩu phải từ 8 đến 20 ký tự")
    private String password;

    @NotEmpty
    private String confirmPassword;

    @NotNull
    private Integer roleId = 2;
}
