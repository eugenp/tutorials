package com.baeldung.verticalslices.author.usecases;

import org.springframework.stereotype.Component;

import com.baeldung.verticalslices.author.domain.AuthorRepository;

@Component
public class EditArticleUseCase {

    private final AuthorRepository authorRepository;

    public void edit(Request request) {
    }

    record Request(){}

    EditArticleUseCase(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }
}
