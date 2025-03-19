package org.spring.mockprojectwebapp.dtos.admin;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HashtagDTO {
    private int hashtagId;
    private String hashtagName;
    private LocalDateTime createdAt;
}
