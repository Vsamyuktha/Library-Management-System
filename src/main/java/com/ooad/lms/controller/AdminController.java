package com.ooad.lms.controller;
import com.ooad.lms.exceptions.DuplicateBookException;
import com.ooad.lms.service.BookBorrowService;
import com.ooad.lms.service.BookReservationService;
import com.ooad.lms.service.BookService;
import com.ooad.lms.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import com.ooad.lms.entity.Book;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/addbooks")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "add-books";
    }

    @PostMapping("/addbooks")
    public String addBook(@ModelAttribute("book") Book book, RedirectAttributes redirectAttributes) {
        try {
            bookService.addBook(book); // Call the service method to add the book
            redirectAttributes.addFlashAttribute("successMessage", "Book added successfully!");
        } catch (DuplicateBookException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/library/admin/addbooks"; // Redirect back to the add-books page
    }

}
