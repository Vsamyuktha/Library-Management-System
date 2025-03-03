package com.ooad.lms.service;

import com.ooad.lms.entity.Borrow;
import java.math.BigDecimal;

import java.util.List;

public interface BorrowService {
    Borrow borrowBook(String username, Long bookId);
    Borrow returnBook(Long bookId);
    List<Borrow> getUserBorrows(String username);
    BigDecimal calculateAmountDue(String username);
}

