package com.java.baeldung.spring.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles")
public class ArticlesController {

    @GetMapping("/{id}")
    public String get(@PathVariable("id") long id) {
        return "Content " + id;
    }
}
