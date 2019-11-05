package com.baeldung.hexagonalarchitecture.adapter.primary;

import com.baeldung.hexagonalarchitecture.hexagon.domain.Article;
import com.baeldung.hexagonalarchitecture.hexagon.ArticleService;
import com.baeldung.hexagonalarchitecture.port.driver.ArticleRestUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleRestController implements ArticleRestUI {

    private final ArticleService articleService;

    @Autowired
    public ArticleRestController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Override
    @GetMapping
    public List<Article> findAll() {
        return articleService.findAll();
    }


    @Override
    @PostMapping
    public Article addArticle(@RequestBody Article article) {
        return articleService.addArticle(article);
    }


    @Override
    @GetMapping(value = "/{id}")
    public Article findOne(@PathVariable("id") long id) {
        return articleService.findOne(id).orElse(null);
    }

}
