package com.ooad.lms.service;
import com.ooad.lms.dao.BorrowRepository;
import com.ooad.lms.entity.Book;
import com.ooad.lms.entity.Borrow;
import com.ooad.lms.entity.Notification;
import com.ooad.lms.entity.Reservation;

import com.ooad.lms.dao.BookRepository;
import com.ooad.lms.dao.ReservationRepository;
import com.ooad.lms.dao.NotificationRepository;

import com.ooad.lms.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookBorrowService implements BorrowService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    BookBorrowService(BorrowRepository borrowRepository) {
        this.borrowRepository = borrowRepository;
    }

    public List<Borrow> findBorrowedBooksByUsername(String username) {
        return borrowRepository.findByUsername(username);
    }


    @Override
    public Borrow borrowBook(String username, Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        Reservation reservation = reservationRepository.findByUsernameAndBookAndStatus(username, book, Reservation.ReservationStatus.COMPLETED).orElse(null);
        //System.out.println(reservation);
        if (reservation != null) {
            reservation.setStatus(Reservation.ReservationStatus.BORROWED);
            reservationRepository.save(reservation);
        } else if (book.getCount() > 0) {
            book.setCount(book.getCount() - 1);
            bookRepository.save(book);
        } else {
            throw new IllegalStateException("Book is not available for borrowing");
        }

        Borrow borrow = new Borrow();
        borrow.setUsername(username);
        borrow.setBook(book);
        borrow.setDateTimeBorrowed(LocalDateTime.now());
        borrow.setDateTimeDue(LocalDateTime.now().plusWeeks(2));
        borrow.setStatus(Borrow.BorrowStatus.PENDING_RETURN);
        borrowRepository.save(borrow);
        return borrow;
    }

    @Override
    public Borrow returnBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        Optional<Reservation> pendingReservation = reservationRepository.findFirstByBookAndStatusOrderByDateTimeReserved(book, Reservation.ReservationStatus.PENDING);

        Borrow borrow = borrowRepository.findFirstByBookAndStatusOrderByDateTimeBorrowedDesc(book, Borrow.BorrowStatus.PENDING_RETURN)
                .orElseThrow(() -> new ResourceNotFoundException("No pending return found for this book"));
        borrow.setStatus(Borrow.BorrowStatus.RETURNED);
        borrow.setDateTimeReturned(LocalDateTime.now());
        borrowRepository.save(borrow);

        if (pendingReservation.isPresent()) {
            Reservation reservation = pendingReservation.get();
            reservation.setStatus(Reservation.ReservationStatus.COMPLETED);
            reservation.setDateTimeAvailable(LocalDateTime.now());
            reservationRepository.save(reservation);

            createNotification(reservation.getUsername(), "The book '" + book.getTitle() + "' you reserved is now available.");
        } else {
            book.setCount(book.getCount() + 1);
            bookRepository.save(book);
        }

        return borrow;
    }

    @Override
    public List<Borrow> getUserBorrows(String username) {
        return borrowRepository.findByUsername(username);
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
    public BigDecimal calculateAmountDue(String username) {
        final BigDecimal DAILY_FINE_RATE = new BigDecimal("2.00"); // Fine per day
        List<Borrow> borrowedBooks = borrowRepository.findByUsername(username); // Get all borrowed books

        BigDecimal totalFine = BigDecimal.ZERO;
        LocalDateTime now = LocalDateTime.now();

        for (Borrow borrow : borrowedBooks) {
            LocalDateTime dueDate = borrow.getDateTimeDue();
            LocalDateTime borrowDate = borrow.getDateTimeBorrowed();

            // Calculate grace period days
            int gracePeriodDays = calculateGracePeriod(borrowDate, dueDate);
            dueDate = dueDate.plusDays(gracePeriodDays); // Add grace period

            // If the adjusted due date is still in the past, apply fine
            if (dueDate.isBefore(now)) {
                long daysLate = ChronoUnit.DAYS.between(dueDate, now);
                totalFine = totalFine.add(DAILY_FINE_RATE.multiply(BigDecimal.valueOf(daysLate)));
            }
        }
        return totalFine;
    }

    // Calculate grace period
    private int calculateGracePeriod(LocalDateTime borrowDate, LocalDateTime dueDate) {
        int graceDays = 0;
        LocalDateTime tempDate = borrowDate;

        while (!tempDate.isAfter(dueDate)) {
            if ((tempDate.getMonthValue() == 12 && tempDate.getDayOfMonth() == 25) || // Christmas
                    (tempDate.getMonthValue() == 1 && tempDate.getDayOfMonth() == 1)) {  // New Year's Day
                graceDays++; // Increment grace period for each holiday found
            }
            tempDate = tempDate.plusDays(1);
        }
        return graceDays;
    }
}
