package com.baeldung.boot.architecture.hexagonal.rest;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HexController {

    @GetMapping
    public void getBooksForUser(){

    }


}