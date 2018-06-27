package com.baeldung.thymeleaf.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.baeldung.thymeleaf.model.Book;
import com.baeldung.thymeleaf.model.Page;
import com.baeldung.thymeleaf.utils.BookUtils;

@Controller
public class BookController {

    private static int currentPage = 1;
    private static int pageSize = 5;

    @RequestMapping(value = "/listBooks", method = RequestMethod.GET)
    public String listBooks(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        page.ifPresent(p -> currentPage = p);
        size.ifPresent(s -> pageSize = s);

        List<Book> books = BookUtils.buildBooks();
        Page<Book> bookPage = new Page<Book>(books, pageSize, currentPage);

        model.addAttribute("books", bookPage.getList());
        model.addAttribute("selectedPage", bookPage.getCurrentPage());
        model.addAttribute("pageSize", pageSize);

        int totalPages = bookPage.getTotalPages();
        model.addAttribute("totalPages", totalPages);

        if (totalPages > 1) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "listBooks.html";
    }
}
