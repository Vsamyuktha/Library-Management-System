package com.ooad.lms.dao;

import com.ooad.lms.entity.Notification;
import com.ooad.lms.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
