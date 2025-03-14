package com.ooad.lms.dao;

import com.ooad.lms.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query( "SELECT COUNT(b) FROM Book b")
    long countBooks();

}
