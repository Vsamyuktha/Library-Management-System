package com.ooad.lms.controller;

import com.ooad.lms.entity.Borrow;
import com.ooad.lms.service.BookBorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/library/admin")
public class BorrowController {

    @Autowired
    private BookBorrowService borrowService;

    @PostMapping("/borrowBook")
    public String borrowBook(@RequestParam String username, @RequestParam Long bookId, Model model) {
        System.out.println("Borrow books controller");
        System.out.println(username+bookId);
        try {
            Borrow borrow = borrowService.borrowBook(username, bookId);
            model.addAttribute("message", "Book borrowed successfully");
        } catch (Exception e) {
            model.addAttribute("message", "Error: " + e.getMessage());
        }
        return "admin-home";
    }

    @PostMapping("/returnBook")
    public String returnBook(@RequestParam Long bookId, Model model) {
        borrowService.returnBook(bookId);
        model.addAttribute("message", "Book returned successfully");
        return "admin-home";
    }

}
