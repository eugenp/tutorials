package com.baeldung.hexagon.web.controller;

import com.baeldung.hexagon.service.IArticleService;
import com.baeldung.hexagon.web.controller.dto.ArticleBriefDto;
import com.baeldung.hexagon.web.controller.dto.ArticleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final IArticleService service;

    @GetMapping
    public List<ArticleBriefDto> articles() {
        return service.articles().stream().map(ArticleBriefDto::of).collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public ArticleDto articleById(@PathVariable Integer id) {
        return ArticleDto.of(service.article(id));
    }
}
