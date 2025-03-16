package org.spring.mockprojectwebapp.repositories;

import org.spring.mockprojectwebapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.username LIKE CONCAT('%', :keyword, '%') OR u.email LIKE CONCAT('%', :keyword, '%')")
    List<User> findByUsernameContainingOrEmailContaining(@Param("keyword") String keyword);

}