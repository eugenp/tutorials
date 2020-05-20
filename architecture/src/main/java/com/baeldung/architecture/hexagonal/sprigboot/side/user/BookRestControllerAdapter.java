package com.baeldung.architecture.hexagonal.sprigboot.side.user;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.architecture.hexagonal.business.bordery.driver.BookRentalPort;
import com.baeldung.architecture.hexagonal.business.bordery.dto.BookDto;

@RestController
@RequestMapping("/books")
public class BookRestControllerAdapter {

    private final BookRentalPort bookRental;

    public BookRestControllerAdapter(BookRentalPort bookRental) {
        this.bookRental = bookRental;
    }

    @GetMapping
    public List<BookDto> searchByAuthor(@RequestParam("name") String name) {
        return bookRental.searchByName(name);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public BookDto donate(@RequestBody BookDto book) {
        return bookRental.donate(book);
    }
}
