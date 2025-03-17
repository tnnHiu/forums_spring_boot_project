package org.spring.mockprojectwebapp.dtos;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
    private int roleId;
    private String roleName;
    private String description;
}
