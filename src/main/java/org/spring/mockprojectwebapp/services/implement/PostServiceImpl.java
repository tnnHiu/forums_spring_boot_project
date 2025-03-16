package org.spring.mockprojectwebapp.services.implement;

import org.spring.mockprojectwebapp.dtos.CategoryDTO;
import org.spring.mockprojectwebapp.dtos.PostDTO;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Override
    public PostDTO savePost(PostDTO postDTO) {
        Post post = mapToEntity(postDTO);
        Post savedPost = postRepository.save(post);
        return mapToDTO(savedPost);
    }

    @Override
    public Optional<PostDTO> findPostById(int postId) {
        return postRepository.findById(postId).map(this::mapToDTO);
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
        return postRepository.findTop10ByOrderByCreatedAtDesc().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void updatePostStatus(int postId, Post.Status status) {
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));

        // Cập nhật trạng thái và thời gian cập nhật
        existingPost.setStatus(status);
        existingPost.setUpdatedAt(LocalDateTime.now());

        // Lưu vào cơ sở dữ liệu
        postRepository.save(existingPost);
    }

    private PostDTO mapToDTO(Post post) {
        return PostDTO.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .imageUrl(post.getImageUrl())
                .description(post.getDescription())
                .author(post.getUser() != null ? post.getUser().getUsername() : null)
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .category(post.getCategory() != null ?
                        CategoryDTO.builder()
                                .categoryId(post.getCategory().getCategoryId())
                                .categoryName(post.getCategory().getCategoryName())
                                .description(post.getCategory().getDescription())
                                .createdAt(post.getCategory().getCreatedAt())
                                .updatedAt(post.getCategory().getUpdatedAt())
                                .build() : null)
                .build();
    }

    private Post mapToEntity(PostDTO postDTO) {
        Post post = new Post();
        post.setId(postDTO.getPostId());
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setImageUrl(postDTO.getImageUrl());
        post.setDescription(postDTO.getDescription());
        if (postDTO.getAuthor() != null) {
            User user = new User();
            user.setUsername(postDTO.getAuthor());
            post.setUser(user);
        }
        post.setCreatedAt(postDTO.getCreatedAt());
        post.setUpdatedAt(postDTO.getUpdatedAt());
        if (postDTO.getCategory() != null) {
            Category category = new Category();
            category.setCategoryId(postDTO.getCategory().getCategoryId());
            category.setCategoryName(postDTO.getCategory().getCategoryName());
            category.setDescription(postDTO.getCategory().getDescription());
            category.setCreatedAt(postDTO.getCategory().getCreatedAt());
            category.setUpdatedAt(postDTO.getCategory().getUpdatedAt());
            post.setCategory(category);
        }
        return post;
    }
}
