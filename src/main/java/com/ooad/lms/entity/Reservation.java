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

    @Column(name = "username", nullable = false)
    private String username;

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

    public Reservation(Book book, String username, LocalDateTime dateTimeReserved, LocalDateTime dateTimeAvailable, ReservationStatus status) {
        this.book = book;
        this.username = username;
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

    public String getUserId() {
        return username;
    }

    public void setUserId(String username) {
        this.username = username;
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
