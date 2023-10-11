package com.baeldung.permitallanonymous.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EcommerceController {

    //can be accessed by only logged-in users
    @GetMapping("/private/showCart")
    public @ResponseBody String showCart() {
        return "Show Cart";
    }

    //can be accessed by both anonymous and authenticated users
    @GetMapping("/public/showProducts")
    public @ResponseBody String listProducts() {
        return "List Products";
    }

    //can be access by only anonymous users not by authenticated users
    @GetMapping("/public/registerUser")
    public @ResponseBody String registerUser() {
        return "Register User";
    }

}

