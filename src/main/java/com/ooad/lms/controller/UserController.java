package com.ooad.lms.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ooad.lms.entity.Borrow;
import com.ooad.lms.entity.Notification;
import com.ooad.lms.entity.Reservation;
import com.ooad.lms.service.BookBorrowService;
import com.ooad.lms.service.BookReservationService;
import com.ooad.lms.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/library/user")
public class UserController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private BookBorrowService borrowService;

    @Autowired
    private BookReservationService reservationService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    public String getHome(@RequestParam String username, Model model) throws JsonProcessingException {
        List<Notification> notifications = notificationService.findNotficationsByUsername(username);
        List<Borrow> borrowedBooks = borrowService.findBorrowedBooksByUsername(username);
        List<Reservation> reservations = reservationService.getUserReservations(username);
        model.addAttribute("username", username);
        model.addAttribute("notifications", notifications);
        model.addAttribute("borrowedBooks", borrowedBooks);
        List<Long> reservedBookIds = reservations.stream()
                .map(r -> r.getBook().getBookId())
                .collect(Collectors.toList());
        model.addAttribute("reservedBookIds", reservedBookIds);

        // Convert to JSON string using Jackson ObjectMapper
        String reservedBookIdsJson = objectMapper.writeValueAsString(reservedBookIds);
        model.addAttribute("reservedBookIdsJson", reservedBookIdsJson);

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
