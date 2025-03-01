package com.ooad.lms.dao;

import com.ooad.lms.entity.Borrow;
import com.ooad.lms.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, Long> {
    List<Borrow> findByUsername(String username);
}
