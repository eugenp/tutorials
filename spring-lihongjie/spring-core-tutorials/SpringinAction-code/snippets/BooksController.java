package com.springbooks.web;
import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/books")
public class BooksController {
  private BookService bookService;
  
  @Inject
  public BookController(BookService bookService) {
    this.bookService = bookService;
  }
  
  @RequestMapping(method=RequestMethod.GET)
  public String listBooks(Model model) {
    model.addAttribute(bookService.getAllBooks());
    return "books/list";
  }
  
  @RequestMapping(value="/{isbn}", method=RequestMethod.GET)
  public String viewBook(@PathVariable("isbn") String isbn, Model model) {
    model.addAttribute(bookService.lookupBookByIsbn(isbn));
    return "books/detail";
  }
}
