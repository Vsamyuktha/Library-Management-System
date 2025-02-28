package com.ooad.lms.service;

import com.ooad.lms.entity.Book;
import com.ooad.lms.dao.BookRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
