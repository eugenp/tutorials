package com.baeldung.web;
import com.baeldung.domain.Book;
import com.baeldung.service.api.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.roo.addon.web.mvc.controller.annotations.config.RooDeserializer;

/**
 * = BookDeserializer
 TODO Auto-generated class documentation
 *
 */
@RooDeserializer(entity = Book.class)
public class BookDeserializer extends JsonObjectDeserializer<Book> {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private BookService bookService;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private ConversionService conversionService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param bookService
     * @param conversionService
     */
    @Autowired
    public BookDeserializer(@Lazy BookService bookService, ConversionService conversionService) {
        this.bookService = bookService;
        this.conversionService = conversionService;
    }
}
