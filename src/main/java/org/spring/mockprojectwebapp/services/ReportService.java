package org.spring.mockprojectwebapp.services;

import org.spring.mockprojectwebapp.dtos.ReportDTO;
import org.spring.mockprojectwebapp.entities.Category;
import org.spring.mockprojectwebapp.entities.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ReportService {
    ReportDTO findById(Integer id);

    void deleteById(Integer id);

    Page<ReportDTO> getReports(String keyword, Pageable pageable);

    ReportDTO update(Integer id, ReportDTO reportDTO, String postStatus, String commentStatus, String userStatus);


}
