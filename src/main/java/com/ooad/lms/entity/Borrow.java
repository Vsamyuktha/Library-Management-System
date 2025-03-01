package com.ooad.lms.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "borrow")
public class Borrow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "borrow_id")
    private Long borrowId;

    @Column(name = "username", nullable = false)
    private String username;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "date_time_borrowed", nullable = false)
    private LocalDateTime dateTimeBorrowed;

    @Column(name = "date_time_due", nullable = false)
    private LocalDateTime dateTimeDue;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BorrowStatus status;

    @Column(name = "date_time_returned")
    private LocalDateTime dateTimeReturned;

    // Enum for borrow status
    public enum BorrowStatus {
        PENDING_RETURN, LATE, RETURNED, SETTLED
    }

    // Constructors
    public Borrow() {
    }

    public Borrow(String username, Book book, LocalDateTime dateTimeBorrowed, LocalDateTime dateTimeDue, BorrowStatus status) {
        this.username = username;
        this.book = book;
        this.dateTimeBorrowed = dateTimeBorrowed;
        this.dateTimeDue = dateTimeDue;
        this.status = status;
    }

    // Getters and Setters
    public Long getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(Long borrowId) {
        this.borrowId = borrowId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDateTime getDateTimeBorrowed() {
        return dateTimeBorrowed;
    }

    public void setDateTimeBorrowed(LocalDateTime dateTimeBorrowed) {
        this.dateTimeBorrowed = dateTimeBorrowed;
    }

    public LocalDateTime getDateTimeDue() {
        return dateTimeDue;
    }

    public void setDateTimeDue(LocalDateTime dateTimeDue) {
        this.dateTimeDue = dateTimeDue;
    }

    public BorrowStatus getStatus() {
        return status;
    }

    public void setStatus(BorrowStatus status) {
        this.status = status;
    }

    public LocalDateTime getDateTimeReturned() {
        return dateTimeReturned;
    }

    public void setDateTimeReturned(LocalDateTime dateTimeReturned) {
        this.dateTimeReturned = dateTimeReturned;
    }
}
