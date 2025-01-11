package com.baeldung.spring.convertmonoobject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest(classes = MonoTransformer.class)
public class MonoTransformerUnitTest {

    @MockBean
    private UserService userService;
    @MockBean
    private BookService bookService;
    private MonoTransformer monoTransformer;

    @BeforeEach
    void setUp() {
        monoTransformer = new MonoTransformer(userService, bookService);
    }

    @Test
    void givenUserId_whenTransformWithMap_thenGetEmail() {
        String userId = "U001";
        Mono<User> userMono = Mono.just(new User(userId, "John", "john@example.com"));
        Mockito.when(userService.getUser(userId))
            .thenReturn(userMono);

        Mono<String> userEmail = userService.getUser(userId)
            .map(User::getEmail);

        StepVerifier.create(userEmail)
            .expectNext("john@example.com")
            .verifyComplete();
    }

    @Test
    void givenActiveUserAndAvailableBook_whenBorrowBook_thenGetBookBorrowResponse() {
        String userId = "U001";
        String bookId = "B001";

        Mono<User> userMono = Mono.just(new User(userId, "John", "john@example.com", true));
        Mono<Book> bookMono = Mono.just(new Book(bookId, "Reactive Programming", 15, true));

        Mockito.when(userService.getUser(userId))
            .thenReturn(userMono);
        Mockito.when(bookService.getBook(bookId))
            .thenReturn(bookMono);

        StepVerifier.create(monoTransformer.borrowBook(userId, bookId))
            .expectNextMatches(bookBorrowResponse -> "Accepted".equals(bookBorrowResponse.getStatus()))
            .verifyComplete();

    }

    @Test
    void givenActiveUserAndAvailableBook_whenBorrowBookWithZip_thenGetBookBorrowResponse() {
        String userId = "U001";
        String bookId = "B001";

        Mono<User> userMono = Mono.just(new User(userId, "John", "john@example.com", true));
        Mono<Book> bookMono = Mono.just(new Book(bookId, "Reactive Programming", 15, true));

        Mockito.when(userService.getUser(userId))
            .thenReturn(userMono);
        Mockito.when(bookService.getBook(bookId))
            .thenReturn(bookMono);

        StepVerifier.create(monoTransformer.borrowBookZip(userId, bookId))
            .expectNextMatches(bookBorrowResponse -> "Accepted".equals(bookBorrowResponse.getStatus()))
            .verifyComplete();

    }

    @Test
    void givenBookId_whenAppliedTransform_thenGetFinalPricedBook() {
        String bookId = "B002";
        Mono<Book> bookMono = Mono.just(new Book(bookId, "Lost Horizons", 10, true));

        Mockito.when(bookService.getBook(bookId))
            .thenReturn(bookMono);

        StepVerifier.create(monoTransformer.getFinalPricedBook(bookId))
            .expectNextMatches(book -> book.getPrice() == 8.8)
            .verifyComplete();
    }

    @Test
    void givenActiveUser_whenAppliedConditionalDiscount_thenGetDiscountedBook() {
        String userId = "U003";
        String bookId = "B003";

        Mono<User> userMono = Mono.just(new User(userId, "Alice", "alice@example.com", true));
        Mono<Book> bookMono = Mono.just(new Book(bookId, "Thread of Time", 15, true));

        Mockito.when(userService.getUser(userId))
            .thenReturn(userMono);
        Mockito.when(bookService.getBook(bookId))
            .thenReturn(bookMono);

        StepVerifier.create(monoTransformer.conditionalDiscount(userId, bookId))
            .expectNextMatches(book -> book.getPrice() == 12)
            .verifyComplete();
    }

    @Test
    void givenInactiveUser_whenAppliedConditionalDiscount_thenGetNoDiscount() {
        String userId = "U004";
        String bookId = "B003";

        Mono<User> userMono = Mono.just(new User(userId, "Bob", "bob@example.com", false));
        Mono<Book> bookMono = Mono.just(new Book(bookId, "Thread of Time", 15, true));

        Mockito.when(userService.getUser(userId))
            .thenReturn(userMono);
        Mockito.when(bookService.getBook(bookId))
            .thenReturn(bookMono);

        StepVerifier.create(monoTransformer.conditionalDiscount(userId, bookId))
            .expectNextMatches(book -> book.getPrice() == 15)
            .verifyComplete();
    }

    @Test
    void givenBookNotAvailable_whenBookBorrow_thenGetStatusRejected() {
        String userId = "U004";
        String bookId = "B004";

        Mono<User> userMono = Mono.just(new User(userId, "Bob", "bob@example.com", false));
        Mono<Book> bookMono = Mono.just(new Book(bookId, "The Quiet Storm", 15, false));

        Mockito.when(userService.getUser(userId))
            .thenReturn(userMono);
        Mockito.when(bookService.getBook(bookId))
            .thenReturn(bookMono);

        StepVerifier.create(monoTransformer.handleErrorBookBorrow(userId, bookId))
            .expectNextMatches(bookBorrowResponse -> "Rejected".equals(bookBorrowResponse.getStatus()))
            .verifyComplete();
    }
}
