package com.ooad.lms.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long reservationId;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "date_time_reserved", nullable = false)
    private LocalDateTime dateTimeReserved;

    @Column(name = "date_time_available")
    private LocalDateTime dateTimeAvailable;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status;

    // Enum for reservation status
    public enum ReservationStatus {
        PENDING, COMPLETED, BORROWED
    }

    // Constructors
    public Reservation() {
    }

    public Reservation(Book book, Long userId, LocalDateTime dateTimeReserved, LocalDateTime dateTimeAvailable, ReservationStatus status) {
        this.book = book;
        this.userId = userId;
        this.dateTimeReserved = dateTimeReserved;
        this.dateTimeAvailable = dateTimeAvailable;
        this.status = status;
    }

    // Getters and Setters
    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getDateTimeReserved() {
        return dateTimeReserved;
    }

    public void setDateTimeReserved(LocalDateTime dateTimeReserved) {
        this.dateTimeReserved = dateTimeReserved;
    }

    public LocalDateTime getdateTimeAvailable() {
        return dateTimeAvailable;
    }

    public void setdateTimeAvailable(LocalDateTime dateTimeAvailable) {
        this.dateTimeAvailable = dateTimeAvailable;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }
}
