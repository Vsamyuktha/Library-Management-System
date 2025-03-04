package com.ooad.lms.dao;

import com.ooad.lms.entity.Book;
import com.ooad.lms.entity.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, Long> {
    Optional<Borrow> findFirstByBookAndStatusOrderByDateTimeBorrowedDesc(Book book, Borrow.BorrowStatus borrowStatus);
    List<Borrow> findByUsername(String username);
    long countByStatus(Borrow.BorrowStatus status);

}
