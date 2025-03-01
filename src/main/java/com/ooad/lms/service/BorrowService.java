package com.ooad.lms.service;

import com.ooad.lms.dao.BorrowRepository;
import com.ooad.lms.dao.NotificationRepository;
import com.ooad.lms.entity.Borrow;
import com.ooad.lms.entity.Notification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowService {
    private final BorrowRepository borrowRepository;

    BorrowService(BorrowRepository borrowRepository) {
        this.borrowRepository = borrowRepository;
    }

    public List<Borrow> findBorrowedBooksByUsername(String username) {
        return borrowRepository.findByUsername(username);
    }
}
