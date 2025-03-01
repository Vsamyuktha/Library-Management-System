package com.ooad.lms.service;

import com.ooad.lms.entity.Borrow;

public interface BorrowService {
    Borrow borrowBook(Long userId, Long bookId);
    Borrow returnBook(Long bookId);
}

