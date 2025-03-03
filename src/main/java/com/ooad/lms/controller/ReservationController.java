package com.ooad.lms.controller;
import com.ooad.lms.entity.Borrow;
import com.ooad.lms.entity.Reservation;
import com.ooad.lms.service.BookBorrowService;
import com.ooad.lms.service.BookReservationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
@RestController
@RequestMapping("/library/reservations")
public class ReservationController {

    @Autowired
    private BookReservationService reservationService;
    @Autowired
    private BookBorrowService borrowService;


    @PostMapping("/reserveBook/{username}/{bookId}")
    public ResponseEntity<Reservation> reserveBook(@PathVariable String username, @PathVariable Long bookId) {
        System.out.println("Received request to reserve book. Username: " + username + ", BookId: " + bookId);
        Reservation reservation = reservationService.reserveBook(username, bookId);
        return ResponseEntity.ok(reservation);
    }

    @GetMapping("/listAllReservations/{username}")
    public ResponseEntity<List<Reservation>> getUserReservations(@PathVariable String username) {
        List<Reservation> reservations = reservationService.getUserReservations(username);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/listAllBorrows/{username}")
    public ResponseEntity<List<Borrow>> getUserBorrows(@PathVariable String username) {
        List<Borrow> borrows = borrowService.getUserBorrows(username);
        return ResponseEntity.ok(borrows);
    }

    @GetMapping("/amountDue/{username}")
    public BigDecimal getAmountDue(@PathVariable String username) {
        return borrowService.calculateAmountDue(username);
    }


    /*
    @PutMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
        return ResponseEntity.ok().build();
    }
    */
}
