package com.baeldung.listbindingexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/books")
public class MultipleBooksController {

    @Autowired
    private BookService bookService;

    @GetMapping(value = "/all")
    public String showAll(Model model) {
        model.addAttribute("books", bookService.findAll());

        return "allBooks";
    }

    @GetMapping(value = "/create")
    public String showCreateForm(Model model) {
        BooksCreationDto booksForm = new BooksCreationDto();

        for (int i = 1; i <= 3; i++) {
            booksForm.addBook(new Book());
        }

        model.addAttribute("form", booksForm);

        return "createBooksForm";
    }

    @GetMapping(value = "/edit")
    public String showEditForm(Model model) {
        List<Book> books = new ArrayList<>();
        bookService.findAll()
            .iterator()
            .forEachRemaining(books::add);

        model.addAttribute("form", new BooksCreationDto(books));

        return "editBooksForm";
    }

    @PostMapping(value = "/save")
    public String saveBooks(@ModelAttribute BooksCreationDto form, Model model) {
        bookService.saveAll(form.getBooks());

        model.addAttribute("books", bookService.findAll());

        return "redirect:/books/all";
    }
}
