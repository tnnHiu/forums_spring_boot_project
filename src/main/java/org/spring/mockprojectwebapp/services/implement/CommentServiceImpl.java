package org.spring.mockprojectwebapp.services.implement;

import org.spring.mockprojectwebapp.dtos.admin.CommentDTO;
import org.spring.mockprojectwebapp.dtos.user.UserCommentDTO;
import org.spring.mockprojectwebapp.entities.Comment;
import org.spring.mockprojectwebapp.repositories.CommentRepository;
import org.spring.mockprojectwebapp.repositories.PostRepository;
import org.spring.mockprojectwebapp.repositories.UserRepository;
import org.spring.mockprojectwebapp.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserCommentDTO> getCommentsByPostId(int postId) {
        List<Comment> comments = commentRepository.findCommentsByPostIdWithUser(postId);
        return comments.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public int getTotalCommentsByPostId(int postId) {
        return commentRepository.countByPostId(postId);
    }

    @Override
    public void saveComment(UserCommentDTO userCommentDTO) {
        Comment comment = mapToEntity(userCommentDTO);
        commentRepository.save(comment);
    }


    @Override
    public void updateCommentStatus(int commentId, Comment.Status status) {
        Comment existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));
        existingComment.setStatus(status);
        existingComment.setUpdatedAt(LocalDateTime.now());
        commentRepository.save(existingComment);
    }

    private UserCommentDTO mapToDTO(Comment comment) {
        return UserCommentDTO.builder()
                .commentId(comment.getId())
                .userId(comment.getUser().getUserId())
                .userName(comment.getUser().getUsername())
//                .avatar(comment.getUser().getAvatar())
                .postId(comment.getPost().getId())
                .comment(comment.getContent())
                .commentStatus(comment.getStatus().toString())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }

    private Comment mapToEntity(UserCommentDTO dto) {
        Comment comment = new Comment();
        comment.setId(dto.getCommentId());
        comment.setPost(postRepository.findById(dto.getPostId()).orElse(null));
        comment.setUser(userRepository.findById(dto.getUserId()).orElse(null));
        comment.setContent(dto.getComment());
        comment.setStatus(Comment.Status.valueOf(dto.getCommentStatus()));
        comment.setCreatedAt(dto.getCreatedAt());
        comment.setUpdatedAt(dto.getUpdatedAt());
        return comment;
    }
}
