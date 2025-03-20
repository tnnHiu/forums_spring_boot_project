package org.spring.mockprojectwebapp.repositories;

import org.spring.mockprojectwebapp.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findByUserUserIdOrderByCreatedAtDesc(int userId);

    @Query("SELECT COUNT(n) FROM Notification n WHERE n.user.userId = :userId AND n.status = org.spring.mockprojectwebapp.entities.Notification.NotificationStatus.UNREAD")
    int countUnreadNotifications(@Param("userId") int userId);
}