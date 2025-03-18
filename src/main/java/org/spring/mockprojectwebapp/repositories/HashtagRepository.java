package org.spring.mockprojectwebapp.repositories;

import org.spring.mockprojectwebapp.entities.Hashtag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface HashtagRepository extends JpaRepository<Hashtag, Integer> {
    @Query("SELECT h FROM Hashtag h WHERE LOWER(h.hashtagName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Hashtag> findByHashtagNameContainingIgnoreCase(@Param("keyword") String keyword, Pageable pageable);
    @Query("SELECT CASE WHEN COUNT(h) > 0 THEN TRUE ELSE FALSE END FROM Hashtag h WHERE h.id = :id")
    boolean existsById(@Param("id") Integer id);
}