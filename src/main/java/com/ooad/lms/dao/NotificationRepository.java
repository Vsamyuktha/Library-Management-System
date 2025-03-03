package com.ooad.lms.dao;

import com.ooad.lms.entity.Notification;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUsernameAndStatus(String username, Notification.NotificationStatus status);
    Notification findNotificationByNotificationId(Long notificationId);

    @Transactional
    @Modifying
    @Query("UPDATE Notification n SET n.status = 'READ' WHERE n.username = :username")
    int markAllAsRead(@Param("username") String username);

}
