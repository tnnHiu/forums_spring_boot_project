package org.spring.mockprojectwebapp.repositories;

import org.spring.mockprojectwebapp.entities.Post;
import org.spring.mockprojectwebapp.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer> {
    // Find all posts by user
    @Query("SELECT p FROM Post p WHERE p.user = :user")
    List<Post> findByUser(@Param("user") User user);

    // Find a specific post by id and user
    @Query("SELECT p FROM Post p WHERE p.id = :id AND p.user = :user")
    Optional<Post> findByIdAndUser(@Param("id") Integer id, @Param("user") User user);

    // Find post by id
    @Query("SELECT p FROM Post p WHERE p.id = :id")
    Optional<Post> findById(@Param("id") Integer id);

    // Get all posts
    @Query("SELECT p FROM Post p")
    List<Post> findAll();

    // Check if post exists by id
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Post p WHERE p.id = :id")
    boolean existsById(@Param("id") Integer id);

    // Delete post by id
    @Modifying
    @Transactional
    @Query("DELETE FROM Post p WHERE p.id = :id")
    void deleteById(@Param("id") Integer id);

    // Find posts by title containing keyword (for search)
    //@Query("SELECT p FROM Post p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    // Add these paginated query methods
    Page<Post> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);

    // Find posts by title or content containing keyword (for search)
    @Query(value = "SELECT * FROM post p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR p.content LIKE CONCAT('%', :keyword, '%')", nativeQuery = true)
    List<Post> findByTitleOrContentContainingIgnoreCase(@Param("keyword") String keyword);

    // Find posts by category name
    @Query("SELECT p FROM Post p WHERE p.category.categoryName = :categoryName")
    List<Post> findByCategory(@Param("categoryName") String categoryName);
}