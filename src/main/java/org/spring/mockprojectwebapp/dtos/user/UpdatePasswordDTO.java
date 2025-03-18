package org.spring.mockprojectwebapp.dtos.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePasswordDTO {

    @NotEmpty(message = "Mật khẩu cũ không được để trống")
    private String oldPassword;

    @NotEmpty(message = "Mật khẩu mới không được để trống")
    @Size(min = 8, max = 20, message = "Mật khẩu mới phải có độ dài từ 8 đến 20 ký tự")
    private String password;

    @NotEmpty(message = "Xác nhận mật khẩu không được để trống")
    private String confirmPassword;
}