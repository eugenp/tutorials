package com.baeldung.hexagonal.domain.service;

import java.util.UUID;

import com.baeldung.hexagonal.domain.Library;
import com.baeldung.hexagonal.domain.repository.LibraryRepository;


import com.baeldung.hexagonal.domain.Book;

public class MemberLibraryService implements LibraryService {
    
    private LibraryRepository libraryRepository;
    
    private final Library library;
    
    public MemberLibraryService(LibraryRepository libraryRespository) {
        this.libraryRepository = libraryRepository;
        library = new Library();
    }
    
    @Override
    public UUID issueBook(String title,
        UUID memberId,
        String memberName) {
        Book book = getBookEntry(title);
        UUID bookId = library.issueBook(book, memberId, memberName);
        libraryRepository.save(book);
        return bookId;
    }
    
    @Override
    public void returnBook(String title) {
        Book book = getBookEntry(title);
        library.returnBook(book);
        libraryRepository.save(book);
    }
    
    private Book getBookEntry(String title) {
        return libraryRepository
            .findByTitle(title)
            .orElseThrow(() -> new RuntimeException(
                "Book with given id does not exist!"));
    }
}
