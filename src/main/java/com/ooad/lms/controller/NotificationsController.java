package com.ooad.lms.controller;

import com.ooad.lms.entity.Notification;
import com.ooad.lms.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/library/notifications")
public class NotificationsController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/read/{id}")
    public ResponseEntity<Notification> read(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.updateNotificationAsRead(id));
    }

    @GetMapping("/mark-all-read/{username}")
    public ResponseEntity<Integer> markAllAsRead(@PathVariable String username) {
        return ResponseEntity.ok(notificationService.updateAllNotificationsAsRead(username));
    }
}
