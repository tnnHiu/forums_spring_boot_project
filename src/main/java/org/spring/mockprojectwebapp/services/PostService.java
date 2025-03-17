package org.spring.mockprojectwebapp.services;

import org.spring.mockprojectwebapp.dtos.PostDTO;
import org.spring.mockprojectwebapp.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostService {
    // Existing methods
    //List<PostDTO> getAllPosts();
    PostDTO getPostById(Integer id);
    PostDTO updatePost(PostDTO postDTO);

    //void updatePostStatus(int postId, Post.Status status);
    void updatePostStatus(int postId, Post.Status status);
    Optional<PostDTO> findPostById(int id);
    PostDTO savePost(PostDTO postDTO);
    Page<PostDTO> getPosts(String keyword, Pageable pageable);
    List<PostDTO> getRecentPosts();
    //List<PostDTO> searchPosts(String keyword);
    List<PostDTO> searchPostsAndContents(String keyword);
    void deletePost(Integer id);

    // Add these pagination methods
    Page<PostDTO> getAllPosts(int page, int size);
    Page<PostDTO> searchPosts(String keyword, int page, int size);
    //void updatePostStatus(Integer id, Post.Status status);
}