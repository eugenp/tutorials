package com.baeldung.httplogging;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @PostMapping
    public ResponseEntity<BookCreatedResponse> create(@RequestBody CreateBookRequest request) {
        BookCreatedResponse response = new BookCreatedResponse(
                UUID.randomUUID(),
                request.title(),
                request.author()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
