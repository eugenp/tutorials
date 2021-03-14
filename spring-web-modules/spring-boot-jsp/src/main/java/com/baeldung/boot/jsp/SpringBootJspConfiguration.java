package com.baeldung.boot.jsp;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.boot.jsp.repository.BookRepository;
import com.baeldung.boot.jsp.repository.impl.InMemoryBookRepository;
import com.baeldung.boot.jsp.repository.model.BookData;

@Configuration
@ComponentScan(basePackages = "com.baeldung.boot.jsp")
public class SpringBootJspConfiguration {

    @Bean
    public BookRepository provideBookRepository() {
        return new InMemoryBookRepository(initialBookData());
    }

    private static Map<String, BookData> initialBookData() {
        Map<String, BookData> initData = new HashMap<>();
        initData.put("ISBN-TEST-1", new BookData("ISBN-TEST-1", "Book 1", "Book 1 Author"));
        initData.put("ISBN-TEST-2", new BookData("ISBN-TEST-2", "Book 2", "Book 2 Author"));
        initData.put("ISBN-TEST-3", new BookData("ISBN-TEST-3", "Book 3", "Book 3 Author"));
        return initData;
    }
}