package org.spring.mockprojectwebapp.services;

import org.spring.mockprojectwebapp.dtos.PostDTO;
import org.spring.mockprojectwebapp.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    void updatePostStatus(int postId, Post.Status status);
    PostDTO findPostById(int id);
    PostDTO savePost(PostDTO postDTO);
    Page<PostDTO> getPosts(String keyword, Pageable pageable);
    List<PostDTO> getRecentPosts();
    Page<PostDTO> filterPosts(String keyword, Integer status, Integer categoryId, Integer isPremium, int page, int size);
    void deletePost(int id);
}