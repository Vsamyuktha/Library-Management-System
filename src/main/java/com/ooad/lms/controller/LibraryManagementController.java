package com.ooad.lms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LibraryManagementController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    // USER

    //  login - post

    // signup - post

    //home
        // borrowed books for the user - get
        // notifications - reserved books
        // overdue

    //search                        -- runqing  -> book table, class
        // random popular books
        // by title
        // by author
        // by genre
        // by publications year

    // Reserve a book - add it to notifications     --vibha
        // If available - for a day put in reservation table
        // If not available -> pending reservations table (bookId -> List<user>)
        // When that book is returned -> send notifications to List<user> (observer - pub/sub)


    // online payment - later


    //ADMIN

    // login

    // Borrow a book
    // return a book
    // update payment status
}
