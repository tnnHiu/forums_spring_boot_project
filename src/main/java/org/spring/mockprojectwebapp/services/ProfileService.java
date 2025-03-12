package org.spring.mockprojectwebapp.services;

import org.spring.mockprojectwebapp.entities.User;

public abstract class ProfileService {
    public abstract Iterable<User> findAll();

    public abstract User findById(Integer userId);
}
