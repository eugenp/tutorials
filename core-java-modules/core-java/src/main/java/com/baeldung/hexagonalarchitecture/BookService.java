package com.baeldung.hexagonalarchitecture;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class BookService {

    BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    public BookGenre getMostPopularGenre() {
        List<Book> books = bookRepository.findAll();
        
        Map<BookGenre, List<Book>> booksByGenre = books.stream()
          .collect(Collectors.groupingBy(Book::getGenre));

        Comparator<Entry<BookGenre, List<Book>>> comparingGenreOccurences = 
          Comparator.comparingInt(entry -> entry.getValue().size());
		
        BookGenre mostPopularGenre = 
          booksByGenre
            .entrySet()
            .stream()
            .max(comparingGenreOccurences)
            .map(Map.Entry::getKey)
            .orElse(null);

        return mostPopularGenre;
    }

    public Book getMostReadBook() {
        List<Book> books = bookRepository.findAll();
        
        Comparator<Book> comparingNumberOfReaders = 
         Comparator.comparingInt(Book::getNumberOfReaders);
		
        Book mostReadBook = 
          books.stream()
            .max(comparingNumberOfReaders)
            .orElse(null);
        
        return mostReadBook;
    }
}
