package com.baeldung.verticalslices.reader;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonal.dto.ArticleDto;


@RestController
@RequestMapping("articles")
class ReaderController {

    private final ReadArticleUseCase viewArticle;
    private final SearchArticleUseCase searchArticle;

    @GetMapping("{id}")
    ResponseEntity<ArticleDto> readArticle(@PathVariable Long id) {
        viewArticle.apply(id);
        return null;
    }

    @GetMapping
    ResponseEntity<SearchArticleUseCase.SearchArticleDto> searchArticle(@RequestParam String slug) {
        return ResponseEntity.of(searchArticle.search(slug));
    }

    ReaderController(ReadArticleUseCase viewArticle, SearchArticleUseCase searchArticle) {
        this.viewArticle = viewArticle;
        this.searchArticle = searchArticle;
    }
}
