package org.spring.mockprojectwebapp.services;

import org.spring.mockprojectwebapp.dtos.CommentDTO;
import org.spring.mockprojectwebapp.entities.Comment;

public interface CommentService {
    void updateCommentStatus(int commentId, Comment.Status status);
}
