package com.ooad.lms.controller;
import com.ooad.lms.entity.Reservation;
import com.ooad.lms.service.BookReservationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/library/reservations")
public class ReservationController {

    @Autowired
    private BookReservationService reservationService;

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

    /*
    @PutMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
        return ResponseEntity.ok().build();
    }
    */
}
