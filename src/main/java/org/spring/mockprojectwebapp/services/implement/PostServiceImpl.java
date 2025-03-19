package org.spring.mockprojectwebapp.services.implement;

import org.spring.mockprojectwebapp.dtos.admin.PostDTO;
import org.spring.mockprojectwebapp.entities.Category;
import org.spring.mockprojectwebapp.entities.Post;
import org.spring.mockprojectwebapp.entities.User;
import org.spring.mockprojectwebapp.repositories.PostRepository;
import org.spring.mockprojectwebapp.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public PostDTO savePost(PostDTO postDTO) {
        Post post = mapToEntity(postDTO);
        post = postRepository.save(post);
        return mapToDTO(post);
    }

    @Override
    public PostDTO findPostById(int postId) {
        Post post = postRepository.findPostById(postId);
        return mapToDTO(post);
    }

    @Override
    public Page<PostDTO> getPosts(String keyword, Pageable pageable) {
        if (keyword == null || keyword.isBlank()) {
            return postRepository.findAll(pageable).map(this::mapToDTO);
        }
        return postRepository.findByTitleContainingOrContentContainingIgnoreCase(keyword, pageable).map(this::mapToDTO);
    }

    @Override
    public List<PostDTO> getRecentPosts() {
        return postRepository.findTop10ByOrderByCreatedAtDesc().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<PostDTO> getUserPosts(int userId) {
        return postRepository.findPostByUser(userId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<PostDTO> searchPosts(String keyword, Pageable pageable) {
        if (keyword == null || keyword.isBlank()) {
            return postRepository.findAll(pageable).map(this::mapToDTO);
        }
        return postRepository.findByTitleContainingOrContentContainingIgnoreCase(keyword, pageable).map(this::mapToDTO);
    }

    @Override
    public void updatePostStatus(int postId, Post.Status status) {
        Post existingPost = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));

        // Cập nhật trạng thái và thời gian cập nhật
        existingPost.setStatus(status);
        existingPost.setUpdatedAt(LocalDateTime.now());

        // Lưu vào cơ sở dữ liệu
        postRepository.save(existingPost);
    }

    private PostDTO mapToDTO(Post post) {
        return PostDTO
                .builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .imageUrl(post.getImageUrl())
                .description(post.getDescription())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .status(post.getStatus().ordinal())
                .isPremium(post.isPremium())
                .categoryName(post.getCategory().getCategoryName())
                .username(post.getUser().getUsername())
                .build();
    }

    private Post mapToEntity(PostDTO postDTO) {
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setImageUrl(postDTO.getImageUrl());
        post.setDescription(postDTO.getDescription());
        post.setCreatedAt(postDTO.getCreatedAt() != null ? postDTO.getCreatedAt() : LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        post.setPremium(postDTO.isPremium());
        post.setStatus(postDTO.getStatus() == 1 ? Post.Status.ACTIVE : Post.Status.INACTIVE);
        // Set User and Category (only need ID)
        User user = new User();
        user.setUserId(postDTO.getUserId());
        post.setUser(user);

        Category category = new Category();
        category.setCategoryId(postDTO.getCategoryId());
        post.setCategory(category);

        return post;
    }
}