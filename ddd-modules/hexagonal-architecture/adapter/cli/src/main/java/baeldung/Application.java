package baeldung;

import baeldung.model.Book;
import baeldung.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@EnableJms
@SpringBootApplication
public class Application implements CommandLineRunner {

    private final LibraryService libraryService;

    @Autowired
    public Application(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        Book book1 = libraryService.addBook("Hitchhiker's guide to the galaxy", "Douglas Adams");
        Book book2 = libraryService.addBook("Foundation", "Isaac Asimov");
        Book book3 = libraryService.addBook("Children of Dune", "Frank Herbert");
        libraryService.addBook("Dune", "Frank Herbert");

        libraryService.lendBook(book1.getId().toString());
        libraryService.lendBook(book2.getId().toString());
        libraryService.lendBook(book3.getId().toString());
        libraryService.returnBook(book2.getId().toString());
    }

}
