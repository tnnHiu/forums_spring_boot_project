package org.spring.mockprojectwebapp.services;

import org.spring.mockprojectwebapp.dtos.user.UserCommentDTO;
import org.spring.mockprojectwebapp.entities.Comment;

import java.util.List;

public interface CommentService {
    void updateCommentStatus(int commentId, Comment.Status status);

    List<UserCommentDTO> getCommentsByPostId(int postId);

    void saveComment(UserCommentDTO userCommentDTO);

}
