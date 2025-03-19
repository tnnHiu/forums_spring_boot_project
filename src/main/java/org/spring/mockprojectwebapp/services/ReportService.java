package org.spring.mockprojectwebapp.services;

import org.spring.mockprojectwebapp.dtos.admin.ReportDTO;
import org.spring.mockprojectwebapp.dtos.user.ReportPostDTO;
import org.spring.mockprojectwebapp.entities.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;


public interface ReportService {
    ReportDTO findById(Integer id);

    void deleteById(Integer id);

    Page<ReportDTO> getReports(String keyword, Pageable pageable);

    ReportDTO update(Integer id, ReportDTO reportDTO, String postStatus, String commentStatus, String userStatus);

    Page<ReportDTO> getFilteredReports(Report.ReportType reportType, Report.Status status, LocalDateTime oldest, Pageable pageable);

    void createPostReport(ReportPostDTO reportDTO);

    void createCommentReport(ReportPostDTO reportDTO);
}
