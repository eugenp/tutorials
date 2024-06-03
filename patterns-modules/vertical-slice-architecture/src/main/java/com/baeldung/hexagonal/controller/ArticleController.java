package com.baeldung.hexagonal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonal.dto.ArticleDto;
import com.baeldung.hexagonal.persistence.entity.Article;
import com.baeldung.hexagonal.service.ArticleService;

@RestController
@RequestMapping("articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    ArticleDto createArticle(@RequestBody ArticleDto dto) {
        var article = mapToEntity(dto);
        article = articleService.create(article);
        return mapToDto(article);
    }

    @PutMapping
    ArticleDto update(@RequestBody ArticleDto dto) {
        var updatedArticle = articleService.update(dto.id(), dto.content());
        return mapToDto(updatedArticle);
    }

    @GetMapping("{id}")
    ResponseEntity<ArticleDto> readArticle(@PathVariable Long id) {
        var article = articleService.findById(id)
            .map(ArticleController::mapToDto);
        return ResponseEntity.of(article);
    }

    private static ArticleDto mapToDto(Article updatedArticle) {
        return new ArticleDto(updatedArticle.getId(), updatedArticle.getName(), updatedArticle.getContent());
    }

    private static Article mapToEntity(ArticleDto dto) {
        return new Article(dto.id(), dto.name(), dto.content());
    }

}
