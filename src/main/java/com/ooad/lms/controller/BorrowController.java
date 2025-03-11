package com.ooad.lms.controller;

import com.ooad.lms.service.BookBorrowService;
import com.ooad.lms.service.BookReservationService;
import com.ooad.lms.service.BookService;
import com.ooad.lms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/library/admin")
public class BorrowController {

    @Autowired
    private BookBorrowService borrowService;

    @Autowired
    private BookReservationService reserveService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @PostMapping("/borrowBook")
    public String borrowBook(@RequestParam String username, @RequestParam Long bookId, Model model) {
        System.out.println("Borrow books controller");
        System.out.println(username+bookId);
        try {
            borrowService.borrowBook(username, bookId);
            model.addAttribute("activeUsers", userService.countActiveUsers());
            model.addAttribute("availableBooks", bookService.countAvailableBooks());
            model.addAttribute("reservedBooks", reserveService.countCompletedReservations());
            model.addAttribute("pendingReservations", reserveService.countPendingReservations());
            model.addAttribute("borrowedBooks", borrowService.countPendingReturnBooks());
            model.addAttribute("message", "Book borrowed successfully");
        } catch (Exception e) {
            model.addAttribute("message", "Error: " + e.getMessage());
        }
        return "admin-home";
    }

    @PostMapping("/returnBook")
    public String returnBook(@RequestParam Long bookId, Model model) {
        borrowService.returnBook(bookId);
        model.addAttribute("activeUsers", userService.countActiveUsers());
        model.addAttribute("availableBooks", bookService.countAvailableBooks());
        model.addAttribute("reservedBooks", reserveService.countCompletedReservations());
        model.addAttribute("pendingReservations", reserveService.countPendingReservations());
        model.addAttribute("borrowedBooks", borrowService.countPendingReturnBooks());
        model.addAttribute("message", "Book returned successfully");
        return "admin-home";
    }

}
