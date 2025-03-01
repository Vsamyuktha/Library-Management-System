package com.ooad.lms.controller;

import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/library/user")
public class UserController {


    @GetMapping
    public String getHome(Model model) {
        return "home";
    }

    // USER

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

}
