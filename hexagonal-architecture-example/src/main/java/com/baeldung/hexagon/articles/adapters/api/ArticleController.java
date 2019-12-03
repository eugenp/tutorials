package com.baeldung.hexagon.articles.adapters.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("articles")
class ArticleController {

    private final ArticleService service;

    ArticleController(final ArticleService service) {
        this.service = service;
    }

    @GetMapping("{articleId}")
    ArticleResponse get(@PathVariable("articleId") final String articleId) {
        return service.get(articleId);
    }

    @PostMapping
    ArticleIdResponse create(@RequestBody final ArticleRequest articleRequest) {
        return service.create(articleRequest);
    }


}
