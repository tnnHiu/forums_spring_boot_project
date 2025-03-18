package org.spring.mockprojectwebapp.services.implement;

import org.spring.mockprojectwebapp.dtos.CommentDTO;
import org.spring.mockprojectwebapp.entities.Comment;
import org.spring.mockprojectwebapp.entities.Post;
import org.spring.mockprojectwebapp.entities.User;
import org.spring.mockprojectwebapp.repositories.CommentRepository;
import org.spring.mockprojectwebapp.repositories.PostRepository;
import org.spring.mockprojectwebapp.repositories.UserRepository;
import org.spring.mockprojectwebapp.services.CommentService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Override
    public void addComment(int postId, String username, String content) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setStatus(Comment.Status.ACTIVE);

        commentRepository.save(comment);
    }

    public List<CommentDTO> getCommentsByPostId(int postId) {
        return commentRepository.findByPostId(postId)
                .stream()
                .map(this::mapToCommentDTO)
                .collect(Collectors.toList());
    }
    public CommentDTO mapToCommentDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setUsername(comment.getUser().getUsername());
        commentDTO.setContent(comment.getContent());
        commentDTO.setCreatedAt(comment.getCreatedAt());

        return commentDTO;
    }

}
