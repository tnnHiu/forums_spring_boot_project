package org.spring.mockprojectwebapp.services.implement;

import org.spring.mockprojectwebapp.dtos.CommentDTO;
import org.spring.mockprojectwebapp.dtos.PostDTO;
import org.spring.mockprojectwebapp.dtos.ReportDTO;
import org.spring.mockprojectwebapp.dtos.UserDTO;
import org.spring.mockprojectwebapp.entities.Comment;
import org.spring.mockprojectwebapp.entities.Post;
import org.spring.mockprojectwebapp.entities.Report;
import org.spring.mockprojectwebapp.entities.User;
import org.spring.mockprojectwebapp.repositories.ReportRepository;
import org.spring.mockprojectwebapp.services.CommentService;
import org.spring.mockprojectwebapp.services.PostService;
import org.spring.mockprojectwebapp.services.ReportService;
import org.spring.mockprojectwebapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final PostService postService;
    private final CommentService commentService;
    private final UserService userService;

    @Autowired
    public ReportServiceImpl(
            ReportRepository reportRepository,
            PostService postService,
            CommentService commentService,
            UserService userService) {
        this.reportRepository = reportRepository;
        this.postService = postService;
        this.commentService = commentService;
        this.userService = userService;
    }

    @Override
    public ReportDTO findById(Integer id) {
        Optional<Report> report = reportRepository.findById(id);
        return report.map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("report not found with id: " + id));
    }

    @Override
    public ReportDTO update(Integer id, ReportDTO reportDTO, String postStatus, String commentStatus, String userStatus) {
        Report existingReport = reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found with id: " + id));

        // Cập nhật trạng thái của đối tượng bị báo cáo (nếu có)
        if (existingReport.getReportedPost() != null && postStatus != null) {
            postService.updatePostStatus(existingReport.getReportedPost().getId(), Post.Status.valueOf(postStatus));
        }
        if (existingReport.getReportedComment() != null && commentStatus != null) {
            commentService.updateCommentStatus(existingReport.getReportedComment().getId(), Comment.Status.valueOf(commentStatus));
        }
        if (existingReport.getReportedUser() != null && userStatus != null) {
            userService.updateUserStatus(existingReport.getReportedUser().getUserId(), User.Status.valueOf(userStatus));
        }

        // Set status của báo cáo thành RESOLVED
        existingReport.setStatus(Report.Status.RESOLVED);
        existingReport.setUpdatedAt(LocalDateTime.now());

        Report updatedReport = reportRepository.save(existingReport);
        return mapToDTO(updatedReport);
    }

    @Override
    public void deleteById(Integer id) {
        if (!reportRepository.existsById(id)) {
            throw new RuntimeException("Report not found with id: " + id);
        }
        reportRepository.deleteById(id);
    }

    @Override
    public Page<ReportDTO> getReports(String keyword, Pageable pageable) {
        Page<Report> reportPage;

        if (keyword == null || keyword.isBlank()) {
            reportPage = reportRepository.findAllReports(pageable);
        } else {
            reportPage = reportRepository.findByReportReasonContainingIgnoreCase(keyword, pageable);
        }
        return reportPage.map(this::mapToDTO);
    }

    // Chuyển đổi từ Entity sang DTO
    private ReportDTO mapToDTO(Report report) {
        return ReportDTO.builder()
                .reportId(report.getReportId())
                .reporterName(report.getReporter().getUsername())
                .reporterId(report.getReporter().getUserId())
                .reportedPostId(report.getReportedPost() != null ? report.getReportedPost().getId() : null)
                .reportedPostTitle(report.getReportedPost() != null ? report.getReportedPost().getTitle() : "N/A")
                .reportedCommentId(report.getReportedComment() != null ? report.getReportedComment().getId() : null)
                .reportedCommentContent(report.getReportedComment() != null ? report.getReportedComment().getContent() : "N/A")
                .reportedUserId(report.getReportedUser() != null ? report.getReportedUser().getUserId() : null)
                .reportedUserName(report.getReportedUser() != null ? report.getReportedUser().getUsername() : "N/A")
                .reason(report.getReason())
                .status(report.getStatus())
                .reportType(report.getReportType())
                .createdAt(report.getCreatedAt())
                .updatedAt(report.getUpdatedAt())
                .build();
    }

    // Chuyển đổi từ DTO sang Entity
    private Report mapToEntity(ReportDTO reportDTO) {
        Report report = new Report();
        report.setReportId(reportDTO.getReportId());
        report.setCreatedAt(reportDTO.getCreatedAt());
        report.setUpdatedAt(reportDTO.getUpdatedAt());
        return report;
    }
}