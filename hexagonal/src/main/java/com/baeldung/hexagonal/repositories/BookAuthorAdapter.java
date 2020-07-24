package com.baeldung.hexagonal.repositories;

import com.baeldung.hexagonal.domain.AuthorDto;
import com.baeldung.hexagonal.domain.BookDto;
import com.baeldung.hexagonal.domain.BookAuthorPersistencePort;
import com.baeldung.hexagonal.entities.Author;
import com.baeldung.hexagonal.entities.Book;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookAuthorAdapter implements BookAuthorPersistencePort {

    final private BookRepository bookRepository;
    final private AuthorRepository authorRepository;

    public BookAuthorAdapter(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public void addBook(BookDto bookDto) {
        Book book = mapBookDtoToBook(bookDto);
        bookRepository.save(book);
    }

    @Override
    public List<BookDto> getAllBook() {
        return bookRepository.findAll()
                .stream()
                .map(this::mapBookToBookDto)
                .collect(Collectors.toList());
    }

    @Override
    public void addAuthor(AuthorDto authorDto) {
        Author author = mapAuthorDtoToAuthor(authorDto);
        authorRepository.save(author);
    }

    @Override
    public List<AuthorDto> getAllAuthor() {
        return authorRepository.findAll()
                .stream()
                .map(this::mapAuthorToAuthorDto)
                .collect(Collectors.toList());
    }

    private BookDto mapBookToBookDto(Book book) {
        return BookDto.builder()
                .name(book.name)
                .authors(book.authors
                                    .stream()
                                    .map(this::mapAuthorToAuthorDto)
                                    .collect(Collectors.toList()))
                .build();
    }

    private Book mapBookDtoToBook(BookDto bookDto) {
        return Book.builder()
                .name(bookDto.name)
                .authors(bookDto.authors
                                    .stream()
                                    .map(this::mapAuthorDtoToAuthor)
                                    .collect(Collectors.toList()))
                .build();
    }

    private AuthorDto mapAuthorToAuthorDto(Author author) {
        return AuthorDto.builder()
                .name(author.name)
                .build();
    }

    private Author mapAuthorDtoToAuthor(AuthorDto authorDto) {
        return Author.builder()
                .name(authorDto.name)
                .build();
    }

}
