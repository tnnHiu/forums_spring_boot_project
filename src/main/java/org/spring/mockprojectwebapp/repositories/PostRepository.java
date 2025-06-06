package org.spring.mockprojectwebapp.repositories;

import org.spring.mockprojectwebapp.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    Post findPostById(int postId);

    @Query("SELECT p FROM Post p WHERE (p.title LIKE %:keyword% OR p.content LIKE %:keyword%) AND p.status = org.spring.mockprojectwebapp.entities.Post.Status.ACTIVE")
    Page<Post> findByTitleContainingOrContentContainingIgnoreCase(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.status = org.spring.mockprojectwebapp.entities.Post.Status.ACTIVE ORDER BY p.createdAt DESC LIMIT 10")
    List<Post> findTop10ByOrderByCreatedAtDesc();

    Page<Post> findAll(Specification<Post> spec, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.user.userId = :userId AND p.status = org.spring.mockprojectwebapp.entities.Post.Status.ACTIVE")
    List<Post> findPostByUser(@Param("userId") int userId);

}
