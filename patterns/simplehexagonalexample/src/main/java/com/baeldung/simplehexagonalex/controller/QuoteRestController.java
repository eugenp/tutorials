package com.baeldung.simplehexagonalex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.simplehexagonalex.domain.QuoteOfTheDay;
import com.baeldung.simplehexagonalex.domain.service.QuoteService;

@RestController
@RequestMapping("/quote")
public class QuoteRestController {

    @Autowired
    private QuoteService quoteService;

    @GetMapping(path = "/{userId}")
    public QuoteOfTheDay getEmployee(@PathVariable("userId") String userId) {
        return quoteService.getQuote(userId);
    }

}
