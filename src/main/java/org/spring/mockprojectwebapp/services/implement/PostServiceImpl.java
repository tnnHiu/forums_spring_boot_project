package org.spring.mockprojectwebapp.services.implement;

import org.spring.mockprojectwebapp.dtos.PostDTO;
import org.spring.mockprojectwebapp.entities.Post;
import org.spring.mockprojectwebapp.repositories.PostRepository;
import org.spring.mockprojectwebapp.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public Page<PostDTO> getAllPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findAll(pageable).map(this::convertToDTO);
    }

    @Override
    public Page<PostDTO> searchPosts(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findByTitleContainingIgnoreCase(keyword, pageable)
                .map(this::convertToDTO);
    }

//    @Override
//    public void updatePostStatus(Integer id, Post.Status status) {
//        Post existingPost = postRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
//
//        // Cập nhật trạng thái và thời gian cập nhật
//        existingPost.setStatus(status);
//        existingPost.setUpdatedAt(LocalDateTime.now());
//
//        // Lưu vào cơ sở dữ liệu
//        postRepository.save(existingPost);
//    }

    @Override
    public PostDTO getPostById(Integer id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bài đăng không tồn tại"));
        return convertToDTO(post);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO) {
        Post post = convertToEntity(postDTO);
        post = postRepository.save(post);
        return convertToDTO(post);
    }

//    @Override
//    public void updatePostStatus(int postId, Post.Status status) {
//        Post existingPost = postRepository.findById(postId)
//                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
//
//        // Cập nhật trạng thái và thời gian cập nhật
//        existingPost.setStatus(status);
//        existingPost.setUpdatedAt(LocalDateTime.now());
//
//        // Lưu vào cơ sở dữ liệu
//        postRepository.save(existingPost);
//    }

//    @Override
//    public List<PostDTO> searchPosts(String keyword) {
//        return postRepository.findByTitleContainingIgnoreCase(keyword)
//                .stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }

//    @Override
//    public void updatePostStatus(int postId, Post.Status status) {
//        Post existingPost = postRepository.findById(postId)
//                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
//
//        // Cập nhật trạng thái và thời gian cập nhật
//        existingPost.setStatus(status);
//        existingPost.setUpdatedAt(LocalDateTime.now());
//
//        // Lưu vào cơ sở dữ liệu
//        postRepository.save(existingPost);
//    }

    @Override
    public List<PostDTO> searchPostsAndContents(String keyword) {
        return postRepository.findByTitleOrContentContainingIgnoreCase(keyword)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deletePost(Integer id) {
        if (!postRepository.existsById(id)) {
            throw new RuntimeException("Bài đăng không tồn tại");
        }
        postRepository.deleteById(id);
    }

    private PostDTO convertToDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setContent(post.getContent());
        postDTO.setCreatedAt(post.getCreatedAt());
        postDTO.setUpdatedAt(post.getUpdatedAt());
        postDTO.setIsPremium(post.isPremium() ? 1 : 0);
        // Convert to enum to both numberic and string
        if (post.getStatus() != null) {
            postDTO.setStatus(post.getStatus().ordinal());
            postDTO.setStatusName(post.getStatus().name());
        }

        // Set author if user exists
        if (post.getUser() != null) {
            postDTO.setUserId(post.getUser().getUserId());
            postDTO.setUserName(post.getUser().getUsername());
        }

        // Set category if exists
        if (post.getCategory() != null) {
            postDTO.setCategoryId(post.getCategory().getCategoryId());
            postDTO.setCategoryName(post.getCategory().getCategoryName());
        }

        return postDTO;
    }

    private Post convertToEntity(PostDTO postDTO) {
        Post post;

        if (postDTO.getId() != null) {
            post = postRepository.findById(postDTO.getId())
                    .orElseThrow(() -> new RuntimeException("Bài đăng không tồn tại"));

            // Update fields
            post.setTitle(postDTO.getTitle());
            post.setContent(postDTO.getContent());

            // Set current timestamp for updatedAt field
            post.setUpdatedAt(LocalDateTime.now());

            // Update category if provided in DTO
            if (postDTO.getCategoryId() != null) {
                // This would require injecting CategoryRepository
                // Category category = categoryRepository.findById(postDTO.getCategoryId()).orElse(null);
                // post.setCategory(category);
            }

            // Update status if provided
            if (postDTO.getStatus() != null) {
                // Update the status based on ordinal value in DTO
                post.setStatus(Post.Status.values()[postDTO.getStatus()]);
            }
        } else {
            // Code for new post remains unchanged
            post = new Post();
            post.setTitle(postDTO.getTitle());
            post.setContent(postDTO.getContent());
        }

        return post;
    }
}