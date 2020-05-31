package com.baeldung.hexagonal.port;

import com.baeldung.hexagonal.domain.Article;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface ArticleWebUIPort {

    @PostMapping
    void createArticle(@RequestBody Article article);

    @GetMapping("/{name}")
    Article findArticleByName(@PathVariable String name);

}
