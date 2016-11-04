package com.baeldung.spring.dispatcher.servlet.web;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
public interface HomeController {
    @GetMapping("/*")
    String home(
      Model model
    );
}
