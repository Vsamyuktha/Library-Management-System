package com.ooad.lms.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/library/admin")
public class AdminController {


    @GetMapping
    public String getHome(@RequestParam String username, Model model) {
        model.addAttribute("username", username);
        return "admin-home";
    }


    //ADMIN

    // Borrow a book
    // return a book
    // update payment status
}
