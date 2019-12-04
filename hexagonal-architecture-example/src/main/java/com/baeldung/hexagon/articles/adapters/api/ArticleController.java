package com.baeldung.hexagon.articles.adapters.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("articles")
class ArticleController {

    private final Logger log = LoggerFactory.getLogger(ArticleController.class);

    private final ArticleService service;

    ArticleController(final ArticleService service) {
        this.service = service;
    }

    @GetMapping("{articleId}")
    ArticleResponse get(@PathVariable("articleId") final String articleId) {
        log.info(">>> HTTP GET Request: retrieve an article with id: \"{}\"", articleId);
        final ArticleResponse articleResponse = service.get(articleId);
        log.info("<<< HTTP GET Response: article: \"{}\", successfully retrieved", articleResponse.title());
        return articleResponse;
    }

    @PostMapping
    ArticleIdResponse create(@RequestBody final ArticleRequest articleRequest) {
        log.info(">>> HTTP POST Request: create an article: \"{}\"", articleRequest.title().value());
        final ArticleIdResponse articleIdResponse = service.create(articleRequest);
        log.info("<<< HTTP POST Response: article with id: \"{}\", successfully created", articleIdResponse.id());
        return articleIdResponse;
    }


}
