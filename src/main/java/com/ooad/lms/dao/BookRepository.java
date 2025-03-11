package com.ooad.lms.dao;

import com.ooad.lms.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query( "SELECT COUNT(b) FROM Book b")
    long countBooks();
    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END " +
            "FROM Book b WHERE b.title = :title " +
            "AND b.publicationYear = :publicationYear")
    boolean existsByTitleAndEditionAndPublicationYear(
            @Param("title") String title,
            @Param("publicationYear") int publicationYear);


}
