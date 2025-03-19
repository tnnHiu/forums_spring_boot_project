package org.spring.mockprojectwebapp.repositories;

import org.spring.mockprojectwebapp.entities.Hashtag;
import org.spring.mockprojectwebapp.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.username LIKE CONCAT('%', :keyword, '%') OR u.email LIKE CONCAT('%', :keyword, '%')")
    List<User> findByUsernameContainingOrEmailContaining(@Param("keyword") String keyword);
    User findByUserId(int userId);
    Page<User> findByUsernameContainingIgnoreCase(String username, Pageable pageable);
    @Query("SELECT u FROM User u WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%')) " + "OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<User> searchUsers(@Param("keyword") String keyword, Pageable pageable);
}
