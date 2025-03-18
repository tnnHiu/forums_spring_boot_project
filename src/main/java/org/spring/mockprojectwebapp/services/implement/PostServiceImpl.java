package org.spring.mockprojectwebapp.services.implement;

import org.spring.mockprojectwebapp.dtos.PostDTO;
import org.spring.mockprojectwebapp.entities.Category;
import org.spring.mockprojectwebapp.entities.Post;
import org.spring.mockprojectwebapp.entities.User;
import org.spring.mockprojectwebapp.repositories.PostRepository;
import org.spring.mockprojectwebapp.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    // deletePost method
    @Override
    public void deletePost(int id) {
        if (!postRepository.existsById(id)) {
            throw new RuntimeException("Post not found with id: " + id);
        }
        postRepository.deleteById(id);
    }

    @Override
    public Page<PostDTO> filterPosts(String keyword, Integer status, Integer categoryId, Integer isPremium, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Specification<Post> spec = Specification.where(null);

        if (keyword != null && !keyword.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.or(
                            cb.like(cb.lower(root.get("title")), "%" + keyword.toLowerCase() + "%")
                            //cb.like(cb.lower(root.get("content")), "%" + keyword.toLowerCase() + "%")
                    )
            );
        }

        if (status != null) {
            spec = spec.and((root, query, cb) -> {
                if (status >= 0 && status < Post.Status.values().length) {
                    return cb.equal(root.get("status"), Post.Status.values()[status]);
                }
                return cb.conjunction();
            });
        }

        if (categoryId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("category").get("categoryId"), categoryId)
            );
        }

        if (isPremium != null) {
            boolean premium = isPremium == 1;
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("isPremium"), premium)
            );
        }

        return postRepository.findAll(spec, pageable).map(this::mapToDTO);
    }

    private PostDTO mapToDTO(Post post) {
        return PostDTO.builder()
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
        post.setStatus(Post.Status.values()[postDTO.getStatus()]);


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