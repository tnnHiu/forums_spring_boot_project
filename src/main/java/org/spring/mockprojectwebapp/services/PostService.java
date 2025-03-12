package org.spring.mockprojectwebapp.services;

import org.spring.mockprojectwebapp.entities.Post;

public interface PostService {
    Iterable<Post> findAll();

    Post findById(Integer id);

    Post save(Post post);

    Post update(Integer id, Post post);

    void deleteById(Integer id);
}
