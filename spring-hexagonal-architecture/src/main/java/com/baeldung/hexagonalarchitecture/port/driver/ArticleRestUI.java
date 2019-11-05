package com.baeldung.hexagonalarchitecture.port.driver;

import com.baeldung.hexagonalarchitecture.hexagon.domain.Article;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ArticleRestUI {

    @GetMapping
    List<Article> findAll();

    @PostMapping
    Article addArticle(@RequestBody Article article);

    @GetMapping(value = "/{id}")
    Article findOne(@PathVariable("id") long id);

}
