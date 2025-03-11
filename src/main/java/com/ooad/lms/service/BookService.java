package com.ooad.lms.service;

import com.ooad.lms.entity.Book;
import com.ooad.lms.dao.BookRepository;
import com.ooad.lms.exceptions.DuplicateBookException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final SearchStrategy.SearchContext searchContext;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        this.searchContext = new SearchStrategy.SearchContext();
    }

    public List<Book> searchBooks(String field, String value) {
        List<Book> books = bookRepository.findAll(); // Fetch all books from DB

        switch (field.toLowerCase()) {
            case "title":
                searchContext.setStrategy(new SearchStrategy.TitleSearchStrategy());
                break;
            case "author":
                searchContext.setStrategy(new SearchStrategy.AuthorSearchStrategy());
                break;
            case "genre":
                searchContext.setStrategy(new SearchStrategy.GenreSearchStrategy());
                break;
            case "year":
                searchContext.setStrategy(new SearchStrategy.YearSearchStrategy());
                break;
            default:
                throw new IllegalArgumentException("Invalid search field: " + field);
        }

        return searchContext.executeSearch(books, value);
    }

    // New function: Get 5 random books
    public List<Book> getRandomBooks(int count) {
        List<Book> books = bookRepository.findAll(); // Fetch all books from DB
        Collections.shuffle(books); // Shuffle the list randomly
        return books.stream().limit(count).collect(Collectors.toList()); // Return only 'count' books
    }

    public long countAvailableBooks() {
        return bookRepository.countBooks();
    }

    public void addBook(Book book) throws DuplicateBookException {
        if (bookRepository.existsByTitleAndEditionAndPublicationYear(
                book.getTitle(),
                book.getPublicationYear())) {

            throw new DuplicateBookException("A book with this title, and publication year already exists");
        }
        bookRepository.save(book);
    }


}
