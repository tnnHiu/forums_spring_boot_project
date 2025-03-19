package org.spring.mockprojectwebapp.controllers.home;


import jakarta.servlet.http.HttpSession;
import org.spring.mockprojectwebapp.dtos.user.ReportPostDTO;
import org.spring.mockprojectwebapp.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("report/")
public class UserReportController {

    @Autowired
    ReportService reportService;

    @PostMapping("/post")
    String reportPost(@RequestParam("postId") int postId, @RequestParam("reason") String reason, Model model, HttpSession session) {
        ReportPostDTO reportPostDTO = new ReportPostDTO();
        int userId = (int) session.getAttribute("userId");
        reportPostDTO.setReporterId(userId);
        reportPostDTO.setPostId(postId);
        reportPostDTO.setReason(reason);

        reportService.createPostReport(reportPostDTO);

        model.addAttribute("success", "Báo cáo bài viết đã được gửi thành công!");
        return "redirect:/post/" + postId;
    }

    @PostMapping("/comment")
    String reportComment(
            @RequestParam("postId") int postId,
            @RequestParam("commentId") int commentId,
            @RequestParam("reason") String reason,
            Model model,
            HttpSession session) {
        ReportPostDTO reportPostDTO = new ReportPostDTO();
        int userId = (int) session.getAttribute("userId");
        reportPostDTO.setReporterId(userId);
        reportPostDTO.setCommentId(commentId);
        reportPostDTO.setReason(reason);

        reportService.createCommentReport(reportPostDTO);

        model.addAttribute("success", "Bình luận đã được gửi thành công!");
        return "redirect:/post/" + postId;
    }


}
