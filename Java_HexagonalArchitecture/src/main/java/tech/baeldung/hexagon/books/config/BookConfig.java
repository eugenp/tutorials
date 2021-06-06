package tech.baeldung.hexagon.books.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.baeldung.hexagon.books.domain.BookPublisher;
import tech.baeldung.hexagon.books.domain.ports.BookMessageSender;
import tech.baeldung.hexagon.books.domain.ports.BookRepository;
import tech.baeldung.hexagon.books.domain.ports.BookService;
import tech.baeldung.hexagon.books.domain.ports.AuthorNotifier;
import tech.baeldung.hexagon.books.domain.ports.AuthorRepository;
//import tech.baeldung.hexagon.books.domain.ports.SocialMediaPublisher;

import java.util.List;

@Configuration
class BookConfig {

    @Bean
   BookPublisher bookEventPublisher(final BookMessageSender eventPublisher,
                                           final List<AuthorNotifier> bookAuthorNotifiers) {
        return new BookPublisher(eventPublisher,
                 bookAuthorNotifiers);
    }

    @Bean
    BookService BookService(final BookRepository bookRepository,
                                  final AuthorRepository authorRepository,
                                  final BookPublisher bookEventPublisher
    ) {
        return new BookService(
                bookRepository,
                authorRepository,
                bookEventPublisher);
    }

}
