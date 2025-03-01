package com.ooad.lms.dao;

import com.ooad.lms.entity.Book;
import com.ooad.lms.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findByUserIdAndBookAndStatus(Long userId, Book book, Reservation.ReservationStatus status);

    Optional<Reservation> findFirstByBookAndStatusOrderByDateTimeReserved(Book book, Reservation.ReservationStatus status);

    List<Reservation> findByUserId(Long userId);

}
