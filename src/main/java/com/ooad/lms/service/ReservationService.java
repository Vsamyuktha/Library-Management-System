package com.ooad.lms.service;

import com.ooad.lms.entity.Reservation;

import java.util.List;

public interface ReservationService {
    Reservation reserveBook(Long userId, Long bookId);
    void returnBook(Long bookId);
    void borrowBook(Long userId, Long bookId);
    List<Reservation> getUserReservations(Long userId);

}
