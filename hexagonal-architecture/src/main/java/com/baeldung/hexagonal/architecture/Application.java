package com.baeldung.hexagonal.architecture;

import com.baeldung.hexagonal.architecture.core.BookRepository;
import com.baeldung.hexagonal.architecture.core.Library;
import com.baeldung.hexagonal.architecture.input.ConsoleLibraryAdapter;
import com.baeldung.hexagonal.architecture.model.Book;
import com.baeldung.hexagonal.architecture.model.User;
import com.baeldung.hexagonal.architecture.repository.BookRepositoryImpl;
import com.baeldung.hexagonal.architecture.repository.UserRepositoryImpl;

public class Application {
    private final ConsoleLibraryAdapter consoleLibraryAdapter;
    private final UserRepositoryImpl userRepository;
    private final BookRepository bookRepository;

    private Application() {
        userRepository = new UserRepositoryImpl();
        bookRepository = new BookRepositoryImpl();
        consoleLibraryAdapter = new ConsoleLibraryAdapter(new Library(userRepository, bookRepository), System.out);
    }

    public static void main(String... args) {
        Application application = new Application();
        application.initializeData();
        application.demoRun();
    }

    private void demoRun() {
        consoleLibraryAdapter.showBooks();
        consoleLibraryAdapter.rentBook(1L, 2L);
        consoleLibraryAdapter.rentBook(2L, 2L);
    }

    private void initializeData() {
        userRepository.saveUsers(
                new User(2L, "User")
        );

        bookRepository.saveBook(new Book(1L, "Java book", null));
        bookRepository.saveBook(new Book(2L, "Spring book", userRepository.loadUser(2L)));
    }
}
