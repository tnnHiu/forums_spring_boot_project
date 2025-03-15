package org.spring.mockprojectwebapp.repositories;

import org.spring.mockprojectwebapp.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
