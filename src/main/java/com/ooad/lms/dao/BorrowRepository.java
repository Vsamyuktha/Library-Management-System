package com.ooad.lms.dao;

import com.ooad.lms.entity.Book;
import com.ooad.lms.entity.Borrow;
import com.ooad.lms.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, Long> {
    Optional<Borrow> findFirstByBookAndStatusOrderByDateTimeBorrowedDesc(Book book, Borrow.BorrowStatus borrowStatus);
}
