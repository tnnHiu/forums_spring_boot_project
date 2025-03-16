package org.spring.mockprojectwebapp.repositories;

import org.spring.mockprojectwebapp.entities.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {
    //    Sắp xếp theo lý do
    @Query("SELECT r FROM Report r JOIN r.reporter u WHERE r.reporter.username LIKE %:keyword% order by r.createdAt desc")
    Page<Report> findByReportReasonContainingIgnoreCase(String keyword, Pageable pageable);

    //    In ra bảng mặc định với pending và ngày mới nhất
    @Query("SELECT r FROM Report r LEFT JOIN FETCH r.reporter LEFT JOIN FETCH r.reportedPost where r.status = 'PENDING' order by r.createdAt desc ")
    Page<Report> findAllReports(Pageable pageable);
}