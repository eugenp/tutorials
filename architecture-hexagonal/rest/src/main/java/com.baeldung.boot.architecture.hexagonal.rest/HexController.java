package com.baeldung.boot.architecture.hexagonal.rest;


import com.baeldung.boot.architecture.hexagonal.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import service.HexService;

import java.util.List;

@RestController
public class HexController {

    @Autowired
    HexService hexService;

    @GetMapping
    public List<User> getBooksForUser(){
        return hexService.getUsers();
    }


}