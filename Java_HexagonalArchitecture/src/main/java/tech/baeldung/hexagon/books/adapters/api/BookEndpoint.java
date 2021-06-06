package tech.baeldung.hexagon.books.adapters.api;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("books")
class BookEndpoint {

    private final BookFacade books;

    BookEndpoint(BookFacade books) {
        this.books = books;
    }

    @GetMapping("{bookId}")
    BookResponse get(@PathVariable("bookId") final String bookId) {
        return books.get(bookId);
    }

    @PostMapping
    BookIdResponse create(@RequestBody final BookRequest bookRequest) {
        return books.create(bookRequest);
    }

}
