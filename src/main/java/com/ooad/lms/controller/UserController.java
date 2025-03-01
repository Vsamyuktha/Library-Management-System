package com.ooad.lms.controller;

import com.ooad.lms.entity.Borrow;
import com.ooad.lms.entity.Notification;
import com.ooad.lms.service.BorrowService;
import com.ooad.lms.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/library/user")
public class UserController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private BorrowService borrowService;

    @GetMapping
    public String getHome(@RequestParam String username, Model model) {
        List<Notification> notifications = notificationService.findNotficationsByUsername(username);
        List<Borrow> borrowedBooks = borrowService.findBorrowedBooksByUsername(username);
        model.addAttribute("username", username);
        model.addAttribute("notifications", notifications);
        model.addAttribute("borrowedBooks", borrowedBooks);
        return "home";
    }

    @GetMapping("/profile")
    public String getProfile(@RequestParam String username, Model model) {
        List<Notification> notifications = notificationService.findNotficationsByUsername(username);
        List<Borrow> borrowedBooks = borrowService.findBorrowedBooksByUsername(username);
        model.addAttribute("username", username);
        model.addAttribute("notifications", notifications);
        model.addAttribute("borrowedBooks", borrowedBooks);
        return "profile";
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
