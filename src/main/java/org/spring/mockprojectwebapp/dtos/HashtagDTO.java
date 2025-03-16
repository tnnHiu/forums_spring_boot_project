package org.spring.mockprojectwebapp.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
//@Builder // thêm để mapping
public class HashtagDTO {
    private int id;
    private String hashtagName;
    private LocalDateTime createdAt;
}