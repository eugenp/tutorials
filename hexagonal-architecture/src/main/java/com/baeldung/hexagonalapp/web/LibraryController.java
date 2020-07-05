package com.baeldung.hexagonalapp.web;

import com.baeldung.hexagonalapp.application.*;
import com.baeldung.hexagonalapp.application.BorrowBookUseCase.BorrowBookCommand;
import com.baeldung.hexagonalapp.application.ReturnBookUseCase.ReturnBookCommand;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
class LibraryController {

    private final ListBooksUseCase listBooksUseCase;
    private final BorrowBookUseCase borrowBookUseCase;
    private final ReturnBookUseCase returnBookUseCase;

    LibraryController(
            ListBooksUseCase listBooksUseCase,
            BorrowBookUseCase borrowBookUseCase,
            ReturnBookUseCase returnBookUseCase) {
        this.listBooksUseCase = listBooksUseCase;
        this.borrowBookUseCase = borrowBookUseCase;
        this.returnBookUseCase = returnBookUseCase;
    }

    @GetMapping
    public List<BookDto> allBooks() {
        return listBooksUseCase.listAll();
    }

    @PostMapping("/{id}/borrow/{borrower}")
    public BookDto borrowBook(@PathVariable Long id, @PathVariable String borrower) {
        BorrowBookCommand command = new BorrowBookCommand(id, borrower);
        return borrowBookUseCase.handle(command);
    }


    @PostMapping("/{id}/return")
    public BookDto returnBook(@PathVariable Long id) {
        ReturnBookCommand command = new ReturnBookCommand(id);
        return returnBookUseCase.handle(command);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BookNotFound.class)
    public String notFound(BookNotFound ex) {
        return ex.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalStateException.class)
    public String notFound(IllegalStateException ex) {
        return ex.getMessage();
    }

}
