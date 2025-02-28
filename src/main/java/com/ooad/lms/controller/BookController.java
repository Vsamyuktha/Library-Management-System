package com.ooad.lms.controller;

import com.ooad.lms.entity.Book;
import com.ooad.lms.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Search books dynamically
    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam String field, @RequestParam String value) {
        return bookService.searchBooks(field, value);
    }

}
