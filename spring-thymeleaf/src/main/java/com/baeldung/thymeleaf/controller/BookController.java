package com.baeldung.thymeleaf.controller;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.baeldung.thymeleaf.model.Book;
import com.baeldung.thymeleaf.service.BookService;

@Controller
public class BookController {


    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/listBooks", method = RequestMethod.GET)
    public String listBooks(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        AtomicInteger currentPage = new AtomicInteger(1);
        AtomicInteger pageSize = new AtomicInteger(5);
        page.ifPresent(p -> currentPage.set(p));
        size.ifPresent(s -> pageSize.set(s));

        Page<Book> bookPage = bookService.findPaginated(PageRequest.of(currentPage.get() - 1, pageSize.get()));

        model.addAttribute("bookPage", bookPage);

        int totalPages = bookPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "listBooks.html";
    }
}
