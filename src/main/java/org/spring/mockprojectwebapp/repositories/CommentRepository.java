package org.spring.mockprojectwebapp.repositories;

import org.spring.mockprojectwebapp.entities.Comment;
import org.spring.mockprojectwebapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("SELECT c FROM Comment c " +
            "JOIN FETCH c.user u " +
            "WHERE c.post.id = :postId AND c.status = org.spring.mockprojectwebapp.entities.Comment.Status.ACTIVE " +
            "ORDER BY c.createdAt DESC")
    List<Comment> findCommentsByPostIdWithUser(@Param("postId") int postId);

    int countByPostId(int postId);
}

