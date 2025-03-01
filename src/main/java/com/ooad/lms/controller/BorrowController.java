package com.ooad.lms.controller;

import com.ooad.lms.entity.Borrow;
import com.ooad.lms.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/borrows")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    @PostMapping
    public ResponseEntity<Borrow> borrowBook(@RequestParam Long userId, @RequestParam Long bookId) {
        Borrow borrow = borrowService.borrowBook(userId, bookId);
        return ResponseEntity.ok(borrow);
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<Void> returnBook(@PathVariable Long id) {
        borrowService.returnBook(id);
        return ResponseEntity.ok().build();
    }
    /*
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Borrow>> getUserBorrows(@PathVariable Long userId) {
        List<Borrow> borrows = borrowService.getUserBorrows(userId);
        return ResponseEntity.ok(borrows);
    }
   */
}
