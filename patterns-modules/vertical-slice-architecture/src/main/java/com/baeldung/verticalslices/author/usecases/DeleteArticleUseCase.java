package com.baeldung.verticalslices.author.usecases;

import org.springframework.stereotype.Component;

import com.baeldung.verticalslices.author.domain.AuthorRepository;

@Component
public class DeleteArticleUseCase {

    private final AuthorRepository authorRepository;

    public void delete(String slug) {
    }

    DeleteArticleUseCase(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }
}
