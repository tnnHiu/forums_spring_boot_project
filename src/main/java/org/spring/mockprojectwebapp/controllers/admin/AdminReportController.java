package org.spring.mockprojectwebapp.controllers.admin;

import jakarta.servlet.http.HttpServletRequest;
import org.spring.mockprojectwebapp.dtos.admin.ReportDTO;
import org.spring.mockprojectwebapp.entities.Report;
import org.spring.mockprojectwebapp.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/admin")
public class AdminReportController {

    @Autowired
    private final ReportService reportService;

    public AdminReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/reports")
    public String showReportsPage(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "reportType", required = false) Report.ReportType reportType,
            @RequestParam(value = "status", required = false) Report.Status status,
            @RequestParam(value = "oldest", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate oldest, // Sử dụng LocalDate thay vì LocalDateTime
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Model model,
            HttpServletRequest request) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ReportDTO> reportDTOPage;

        if (keyword != null && !keyword.isEmpty()) {
            reportDTOPage = reportService.getReports(keyword, pageable);
        } else {
            LocalDateTime oldestDateTime = oldest != null ? oldest.atStartOfDay() : null; // Chuyển đổi LocalDate sang LocalDateTime
            reportDTOPage = reportService.getFilteredReports(reportType, status, oldestDateTime, pageable);
        }

        model.addAttribute("reportDTOs", reportDTOPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", reportDTOPage.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("reportType", reportType);
        model.addAttribute("status", status);
        model.addAttribute("oldest", oldest);
        model.addAttribute("title", "Quản lý báo cáo");
        model.addAttribute("currentUri", request.getRequestURI());

        model.addAttribute("reportDTO", new ReportDTO());

        return "admin/report/index";
    }

    @GetMapping("/reports/edit/{id}")
    public ResponseEntity<?> getReportById(@PathVariable("id") int id) {
        ReportDTO report = reportService.findById(id);
        if (report == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Report not found");
        }
        return ResponseEntity.ok(report);
    }

    @PostMapping("/reports/update/{id}")
    public String updateReport(
            @PathVariable("id") int id,
            @RequestParam(value = "postStatus", required = false) String postStatus,
            @RequestParam(value = "commentStatus", required = false) String commentStatus,
            @RequestParam(value = "userStatus", required = false) String userStatus,
            Model model) {

        try {
            // Cập nhật trạng thái của đối tượng bị báo cáo và set status của báo cáo thành RESOLVED
            reportService.update(id, new ReportDTO(), postStatus, commentStatus, userStatus);
            model.addAttribute("successMessage", "Cập nhật báo cáo thành công!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi cập nhật báo cáo: " + e.getMessage());
        }

        return "redirect:/admin/reports";
    }

    // Xóa báo cáo
    @PostMapping("/reports/delete/{id}")
    public String deleteCategory(@PathVariable("id") int id, Model model) {
        try {
            reportService.deleteById(id);
            model.addAttribute("successMessage", "Xóa danh mục thành công!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi xóa danh mục: " + e.getMessage());
        }
        return "redirect:/admin/reports";
    }
}