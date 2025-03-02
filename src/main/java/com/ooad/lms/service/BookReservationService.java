package com.ooad.lms.service;

import com.ooad.lms.entity.Book;
import com.ooad.lms.entity.Notification;
import com.ooad.lms.entity.Reservation;

import com.ooad.lms.dao.BookRepository;
import com.ooad.lms.dao.ReservationRepository;
import com.ooad.lms.dao.NotificationRepository;


import com.ooad.lms.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookReservationService implements ReservationService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public Reservation reserveBook(String username, Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        // Book book = bookRepository.findById(bookId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));
        if (book.getCount() > 0) {
            book.setCount(book.getCount() - 1);
            bookRepository.save(book);

            Reservation reservation = new Reservation();
            reservation.setUsername(username);
            reservation.setBook(book);
            reservation.setDateTimeReserved(LocalDateTime.now());
            reservation.setStatus(Reservation.ReservationStatus.COMPLETED);
            reservation = reservationRepository.save(reservation);

            createNotification(username, "Book '" + book.getTitle() + "' is ready to be picked up.");

            return reservation;
        } else {
            Reservation reservation = new Reservation();
            reservation.setUsername(username);
            reservation.setBook(book);
            reservation.setDateTimeReserved(LocalDateTime.now());
            reservation.setStatus(Reservation.ReservationStatus.PENDING);
            reservation = reservationRepository.save(reservation);

            createNotification(username, "Book '" + book.getTitle() + "' is currently unavailable. You will be notified when it becomes available.");

            return reservation;
        }
    }

    @Override
    public void returnBook(Long bookId) {

    }

    @Override
    public void borrowBook(String username, Long bookId) {

    }




    private void createNotification(String username, String message) {
        Notification notification = new Notification();
        notification.setUsername(username);
        notification.setDateTime(LocalDateTime.now());
        notification.setMessage(message);
        notification.setStatus(Notification.NotificationStatus.UNREAD);
        notificationRepository.save(notification);
    }

    @Override
    public List<Reservation> getUserReservations(String username) {
        return reservationRepository.findByUsername(username);
    }
}
