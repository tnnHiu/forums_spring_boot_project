package org.spring.mockprojectwebapp.repositories;

import org.spring.mockprojectwebapp.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByPostId(int postId);
}
