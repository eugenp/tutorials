package com.baeldung.spring.convertmonoobject;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class MonoTransformer {

    private final UserService userService;
    private final BookService bookService;

    public MonoTransformer(UserService userService, BookService bookService) {
        this.userService = userService;
        this.bookService = bookService;
    }

    public Mono<BookBorrowResponse> borrowBook(String userId, String bookId) {
        return userService.getUser(userId)
            .flatMap(user -> {
                if (!user.isActive()) {
                    return Mono.error(new RuntimeException("User is not an active member"));
                }
                return bookService.getBook(bookId);
            })
            .flatMap(book -> {
                if (!book.isAvailable()) {
                    return Mono.error(new RuntimeException("Book is not available"));
                }
                return Mono.just(new BookBorrowResponse(userId, bookId, "Accepted"));
            });
    }

    public Mono<BookBorrowResponse> borrowBookZip(String userId, String bookId) {
        Mono<User> userMono = userService.getUser(userId)
            .switchIfEmpty(Mono.error(new RuntimeException("User not found")));
        Mono<Book> bookMono = bookService.getBook(bookId)
            .switchIfEmpty(Mono.error(new RuntimeException("Book not found")));
        return Mono.zip(userMono, bookMono, (user, book) -> new BookBorrowResponse(userId, bookId, "Accepted"));
    }

    public Mono<Book> applyDiscount(Mono<Book> bookMono) {
        return bookMono.map(book -> {
            book.setPrice(book.getPrice() - book.getPrice() * 0.2);
            return book;
        });
    }

    public Mono<Book> applyTax(Mono<Book> bookMono) {
        return bookMono.map(book -> {
            book.setPrice(book.getPrice() + book.getPrice() * 0.1);
            return book;
        });
    }

    public Mono<Book> getFinalPricedBook(String bookId) {
        return bookService.getBook(bookId)
            .transform(this::applyTax)
            .transform(this::applyDiscount);
    }

    public Mono<Book> conditionalDiscount(String userId, String bookId) {
        return userService.getUser(userId)
            .filter(User::isActive)
            .flatMap(user -> bookService.getBook(bookId)
                .transform(this::applyDiscount))
            .switchIfEmpty(bookService.getBook(bookId))
            .switchIfEmpty(Mono.error(new RuntimeException("Book not found")));
    }

    public Mono<BookBorrowResponse> handleErrorBookBorrow(String userId, String bookId) {
        return borrowBook(userId, bookId).onErrorResume(ex -> Mono.just(new BookBorrowResponse(userId, bookId, "Rejected")));
    }
}
