package com.baeldung.hexarch.boostrore.controller;

import com.baeldung.hexarch.boostrore.controller.dto.Author;
import com.baeldung.hexarch.boostrore.controller.dto.BookResponse;
import com.baeldung.hexarch.boostrore.controller.dto.CreateBookRequest;
import com.baeldung.hexarch.boostrore.domain.BookOperations;
import com.baeldung.hexarch.boostrore.model.Book;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class BookController {
    private final BookOperations bookOperations;

    @PutMapping(value = "/api/v1/books",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    private BookResponse createBook(
            @RequestBody final CreateBookRequest createBookRequest) {
        Set<String> authorEmailSet = createBookRequest.getAuthors()
                .stream().map(Author::getEmailId)
                .collect(Collectors.toSet());

        Book book = bookOperations.create(createBookRequest.getIsbn(),
                createBookRequest.getTitle(), authorEmailSet);

//        book.getAuthors().stream().map()
        return BookResponse.builder()
                .isbn(book.getIsbn()).title(book.getName())
                .build();
    }
}
