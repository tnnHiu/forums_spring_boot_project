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
    // Tìm hashtag theo tên (không phân biệt hoa thường)
    @Query("SELECT h FROM Hashtag h WHERE LOWER(h.hashtagName) = LOWER(:hashtagName)")
    Optional<Hashtag> findByHashtagNameIgnoreCase(@Param("hashtagName") String hashtagName);

    // Tìm kiếm hashtag theo từ khóa trong tên (không phân biệt hoa thường) với phân trang
    @Query("SELECT h FROM Hashtag h WHERE LOWER(h.hashtagName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Hashtag> findByHashtagNameContainingIgnoreCase(@Param("keyword") String keyword, Pageable pageable);

    // Kiểm tra sự tồn tại của hashtag theo tên (không phân biệt hoa thường)
    @Query("SELECT CASE WHEN COUNT(h) > 0 THEN TRUE ELSE FALSE END FROM Hashtag h WHERE LOWER(h.hashtagName) = LOWER(:hashtagName)")
    boolean existsByHashtagNameIgnoreCase(@Param("hashtagName") String hashtagName);

    // Kiểm tra sự tồn tại của hashtag theo id
    @Query("SELECT CASE WHEN COUNT(h) > 0 THEN TRUE ELSE FALSE END FROM Hashtag h WHERE h.id = :id")
    boolean existsById(@Param("id") Integer id);

    // Xóa hashtag theo tên (không phân biệt hoa thường)
    @Modifying
    @Transactional
    @Query("DELETE FROM Hashtag h WHERE LOWER(h.hashtagName) = LOWER(:hashtagName)")
    void deleteByHashtagNameIgnoreCase(@Param("hashtagName") String hashtagName);

    // Xóa hashtag theo id
    @Modifying
    @Transactional
    @Query("DELETE FROM Hashtag h WHERE h.id = :id")
    void deleteById(@Param("id") Integer id);
}