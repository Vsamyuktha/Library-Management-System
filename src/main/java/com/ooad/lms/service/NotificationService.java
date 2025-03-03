package com.ooad.lms.service;

import com.ooad.lms.dao.NotificationRepository;
import com.ooad.lms.entity.Notification;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> findNotficationsByUsername(String username) {
        List<Notification> notifications = notificationRepository.findByUsernameAndStatus(username, Notification.NotificationStatus.UNREAD);
        notifications.sort(Comparator.comparing(Notification::getDateTime).reversed());
        return notifications;
    }

    public Notification updateNotificationAsRead(Long notificationId) {
        Notification notification = notificationRepository.findNotificationByNotificationId(notificationId);
        notification.setStatus(Notification.NotificationStatus.READ);
        notificationRepository.save(notification);
        return notification;
    }

    public int updateAllNotificationsAsRead(String username) {
        return notificationRepository.markAllAsRead(username);
    }
}
