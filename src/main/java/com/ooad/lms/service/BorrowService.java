package com.ooad.lms.service;

import com.ooad.lms.entity.Borrow;

public interface BorrowService {
    Borrow borrowBook(String username, Long bookId);
    Borrow returnBook(Long bookId);
}

