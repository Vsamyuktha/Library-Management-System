package com.ooad.lms.service;

import com.ooad.lms.entity.Reservation;

import java.util.List;

public interface ReservationService {
    Reservation reserveBook(String username, Long bookId);
    void returnBook(Long bookId);
    void borrowBook(String unsername, Long bookId);
    List<Reservation> getUserReservations(String username);

}
