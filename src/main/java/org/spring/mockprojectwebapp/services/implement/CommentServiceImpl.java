package org.spring.mockprojectwebapp.services.implement;

import org.spring.mockprojectwebapp.dtos.CommentDTO;
import org.spring.mockprojectwebapp.entities.Comment;
import org.spring.mockprojectwebapp.repositories.CommentRepository;
import org.spring.mockprojectwebapp.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public void updateCommentStatus(int commentId, Comment.Status status) {
        Comment existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));

        // Cập nhật trạng thái và thời gian cập nhật
        existingComment.setStatus(status);
        existingComment.setUpdatedAt(LocalDateTime.now());

        // Lưu vào cơ sở dữ liệu
        commentRepository.save(existingComment);
    }

    // Chuyển đổi từ Entity sang DTO
    private CommentDTO mapToDTO(Comment comment) {
        return CommentDTO.builder()
                .id(comment.getId())
                .postId(comment.getPost().getId())
                .userId(comment.getUser().getUserId())
                .content(comment.getContent())
                .status(comment.getStatus())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }
}