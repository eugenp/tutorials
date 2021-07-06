package com.baeldung.hexagonalarchitecture.book.port.inbound;

import com.baeldung.hexagonalarchitecture.book.usecase.CreateBookUsecase;
import com.baeldung.hexagonalarchitecture.book.usecase.dto.BookDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/books")
public class BookController {

    CreateBookUsecase createBookUsecase;

    @PostMapping
    public void create(BookDto book) {
        createBookUsecase.create(book);
    }
}
