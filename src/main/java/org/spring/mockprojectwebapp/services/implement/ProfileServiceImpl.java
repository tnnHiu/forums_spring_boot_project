package org.spring.mockprojectwebapp.services.implement;

import org.spring.mockprojectwebapp.entities.User;
import org.spring.mockprojectwebapp.repositories.ProfileRepository;
import org.spring.mockprojectwebapp.services.ProfileService;

public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Iterable<User> findAll() {
        return null;
    }

    @Override
    public User findById(Integer userId) {
        return null;
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public User status(Integer userId, User user) {
        return null;
    }

    @Override
    public void deleteById(Integer userId) {

    }
}
