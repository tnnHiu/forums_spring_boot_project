package org.spring.mockprojectwebapp.services;

import org.spring.mockprojectwebapp.dtos.PostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostService {
    PostDTO findPostById(int id);
    PostDTO savePost(PostDTO postDTO);
    Page<PostDTO> getPosts(String keyword, Pageable pageable);
    List<PostDTO> getRecentPosts();
}
