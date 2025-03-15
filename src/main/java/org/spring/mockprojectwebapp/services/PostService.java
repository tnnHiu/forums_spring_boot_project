package org.spring.mockprojectwebapp.services;

import org.spring.mockprojectwebapp.dtos.PostDTO;
import org.spring.mockprojectwebapp.entities.Post;

public interface PostService {
    void updatePostStatus(int postId, Post.Status status);
}
