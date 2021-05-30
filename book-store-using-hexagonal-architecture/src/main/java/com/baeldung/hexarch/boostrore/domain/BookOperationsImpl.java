package com.baeldung.hexarch.boostrore.domain;

import com.baeldung.hexarch.boostrore.model.Author;
import com.baeldung.hexarch.boostrore.model.Book;
import com.baeldung.hexarch.boostrore.repository.AuthorsRepository;
import com.baeldung.hexarch.boostrore.repository.BooksRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class BookOperationsImpl implements BookOperations {
    private final BooksRepository booksRepository;
    private final AuthorsRepository authorsRepository;

    @Override
    public Book create(String isbn, String bookName, Set<String> authors) {
        // if the book with given isbn already exists then let us
        // throw an error
        if (booksRepository.findByIsbn(isbn).isPresent()) {
            throw new RuntimeException("A Book with ISBN " + isbn
                    + " already exists");
        }
        //Ask for the Author objects
        Set<Author> authorSet = authors.stream()
                .map(this::upsetAuthor)
                .collect(Collectors.toSet());

        // create the book object and associate the authors
        Book book = new Book();
        book.setName(bookName);
        book.setIsbn(isbn);
        book.setAuthors(authorSet);

        // persist the book object
        return booksRepository.save(book);
    }

    @Override
    public Book getBookById(long id) {
        // Get the book object using the id
        return booksRepository.findById(id).get();
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        // Get the book by ISBN
        return booksRepository.findByIsbn(isbn).get();
    }

    @Override
    public Set<Author> getAuthorsOfBook(long id) {
        return this.getBookById(id).getAuthors();
    }

    private Author upsetAuthor(String email) {
        Optional<Author> byEmailId = authorsRepository.findByEmailId(email);
        // Check if a author already exists with given email id. if not create the Author
        // else return the author we found
        if (byEmailId.isEmpty()) {
            throw new RuntimeException("Author not found: email id "
                    + email);
        }
        return byEmailId.get();
    }
}
