package com.baeldung.dashboard.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.baeldung.dashboard.models.Quotes;

@Controller
public class Dashboard {
    
    @GetMapping("/greeting")
    public String getGreeting(Model model) {
        model.addAttribute("host", System.getProperty("os.name"));
        return "greeting";
    }
    
    @GetMapping("/quote")
    public String getQuote(Model model) throws InterruptedException {
        Random r = new Random();
        int day = r.nextInt(7);
        String quote = Quotes.list.get(day);
        
        int wait = r.nextInt(6);
        Thread.currentThread().sleep(wait);
        model.addAttribute("quote", quote);
        
        return "quote";
    }
    
    @GetMapping("/time")
    public String getTime(Model model) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("hh:mm:ss a");
        LocalDateTime now = LocalDateTime.now();
        model.addAttribute("time", fmt.format(now));
        return "time";
    }
}
