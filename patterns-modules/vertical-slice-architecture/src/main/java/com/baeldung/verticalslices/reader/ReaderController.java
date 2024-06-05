package com.baeldung.verticalslices.reader;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonal.dto.ArticleDto;


@RestController
class ReaderController {

    private final ReadArticleUseCase viewArticle;

    @GetMapping("{id}")
    ResponseEntity<ArticleDto> readArticle(@PathVariable Long id) {
        viewArticle.apply(id);
        return null;
    }

    ReaderController(ReadArticleUseCase viewArticle) {
        this.viewArticle = viewArticle;
    }
}
