package org.spring.mockprojectwebapp.dtos.admin;

import lombok.*;
import org.spring.mockprojectwebapp.entities.Report.ReportType;
import org.spring.mockprojectwebapp.entities.Report.Status;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {
    private int reportId;
    private int reporterId;
    private String reporterName;
    private Integer reportedPostId;
    private String reportedPostTitle;
    private Status reportedPostStatus ;
    private Integer reportedCommentId;
    private String reportedCommentContent;
    private String reportedCommentStatus;
    private Integer reportedUserId;
    private String reportedUserName;
    private String reportedUserStatus;
    private String reason;
    private Status status;
    private ReportType reportType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
