package com.baeldung.hexagonal.application;


import com.baeldung.hexagonal.domain.Article;

import java.util.Optional;

public class LibraryRegistry {

    private final LibraryRepository libraryRepository;
    private final LibraryInput libraryInput;

    public LibraryRegistry(LibraryRepository libraryRepository, LibraryInput libraryInput) {
        this.libraryRepository = libraryRepository;
        this.libraryInput = libraryInput;
    }

    public Optional<Article> searchByTitle() {
        String title = libraryInput.getTitle();
        return libraryRepository.findBy(title);
    }

}
