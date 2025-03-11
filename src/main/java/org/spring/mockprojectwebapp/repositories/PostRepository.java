package org.spring.mockprojectwebapp.repositories;

import org.spring.mockprojectwebapp.entities.Post;
import org.spring.mockprojectwebapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByUser(User user);
    Optional<Post> findByIdAndUser(Integer id, User user);
}