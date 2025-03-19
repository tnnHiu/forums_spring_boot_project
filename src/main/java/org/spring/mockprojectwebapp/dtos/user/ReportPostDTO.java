package org.spring.mockprojectwebapp.dtos.user;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportPostDTO {
    private int postId;
    private int reporterId;
    private int commentId;
    private String reason;
}
