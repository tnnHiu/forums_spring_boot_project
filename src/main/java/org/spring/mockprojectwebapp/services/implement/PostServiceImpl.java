package org.spring.mockprojectwebapp.services.implement;

import org.spring.mockprojectwebapp.entities.Post;
import org.spring.mockprojectwebapp.repositories.PostRepository;
import org.spring.mockprojectwebapp.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Iterable<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Post findById(Integer id) {
        Optional<Post> post = postRepository.findById(id);
        return post.orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
    }

    @Override
    public Post save(Post post) {
        post.setCreatedAt(post.getCreatedAt() == null ? java.time.LocalDateTime.now() : post.getCreatedAt());
        post.setUpdatedAt(java.time.LocalDateTime.now());
        return postRepository.save(post);
    }

    @Override
    public Post update(Integer id, Post updatedPost) {
        Post existingPost = findById(id);
        existingPost.setTitle(updatedPost.getTitle());
        existingPost.setContent(updatedPost.getContent());
        existingPost.setUpdatedAt(java.time.LocalDateTime.now());
        return postRepository.save(existingPost);
    }

    @Override
    public void deleteById(Integer id) {
        if (!postRepository.existsById(id)) {
            throw new RuntimeException("Post not found with id: " + id);
        }
        postRepository.deleteById(id);
    }
}
