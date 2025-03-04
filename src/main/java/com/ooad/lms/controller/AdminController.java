package com.ooad.lms.controller;
import com.ooad.lms.service.BookBorrowService;
import com.ooad.lms.service.BookReservationService;
import com.ooad.lms.service.BookService;
import com.ooad.lms.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping("/library/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookBorrowService borrowsService;

    @Autowired
    private BookReservationService reserveService;

    @GetMapping
    public String getHome(@RequestParam String username, Model model) {
        model.addAttribute("username", username);
        model.addAttribute("activeUsers", userService.countActiveUsers());
        model.addAttribute("availableBooks", bookService.countAvailableBooks());
        model.addAttribute("reservedBooks", reserveService.countCompletedReservations());
        model.addAttribute("pendingReservations", reserveService.countPendingReservations());
        model.addAttribute("borrowedBooks", borrowsService.countPendingReturnBooks());

        return "admin-home";
    }


}
