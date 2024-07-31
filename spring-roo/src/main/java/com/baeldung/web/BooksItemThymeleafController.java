package com.baeldung.web;
import com.baeldung.domain.Book;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.thymeleaf.annotations.RooThymeleaf;

/**
 * = BooksItemThymeleafController
 TODO Auto-generated class documentation
 *
 */
@RooController(entity = Book.class, type = ControllerType.ITEM)
@RooThymeleaf
public class BooksItemThymeleafController {
}
