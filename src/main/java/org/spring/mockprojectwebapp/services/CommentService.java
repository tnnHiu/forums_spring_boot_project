package org.spring.mockprojectwebapp.services;

import org.spring.mockprojectwebapp.dtos.CommentDTO;

import java.util.List;

public interface CommentService {
    void addComment(int postId, String username, String content);

    List<CommentDTO> getCommentsByPostId(int postId);
}
