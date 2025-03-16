package org.spring.mockprojectwebapp.services;

import org.spring.mockprojectwebapp.dtos.PostDTO;
import org.spring.mockprojectwebapp.entities.Post;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {
    // Existing methods
    //List<PostDTO> getAllPosts();
    PostDTO getPostById(Integer id);
    PostDTO updatePost(PostDTO postDTO);

    //void updatePostStatus(int postId, Post.Status status);

    //List<PostDTO> searchPosts(String keyword);
    List<PostDTO> searchPostsAndContents(String keyword);
    void deletePost(Integer id);

    // Add these pagination methods
    Page<PostDTO> getAllPosts(int page, int size);
    Page<PostDTO> searchPosts(String keyword, int page, int size);

    //void updatePostStatus(Integer id, Post.Status status);
}