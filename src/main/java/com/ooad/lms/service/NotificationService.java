package com.ooad.lms.service;

import com.ooad.lms.dao.NotificationRepository;
import com.ooad.lms.entity.Notification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> findNotficationsByUsername(String username) {
        return notificationRepository.findByUsername(username);
    }
}
