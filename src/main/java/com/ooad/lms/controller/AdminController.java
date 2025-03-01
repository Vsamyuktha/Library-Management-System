package com.ooad.lms.controller;

import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/library/admin")
public class AdminController {


    @GetMapping
    public String getHome(Model model) {
        return "admin-home";
    }


    //ADMIN

    // Borrow a book
    // return a book
    // update payment status
}
