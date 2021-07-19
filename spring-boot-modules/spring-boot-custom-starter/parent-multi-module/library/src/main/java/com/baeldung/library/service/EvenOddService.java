package com.baeldung.library.service;

import org.springframework.stereotype.Service;

@Service
public class EvenOddService {
 
    public String isEvenOrOdd(Integer number) {
        return number % 2 == 0 ? "Even" : "Odd";
    }
}