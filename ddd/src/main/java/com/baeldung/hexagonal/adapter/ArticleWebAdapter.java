package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.domain.Article;
import com.baeldung.hexagonal.port.ArticleServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

public class ArticleWebAdapter {

    @Autowired
    private ArticleServicePort articleServicePort;

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ResponseEntity createNewArticle(@Valid String name, String text) {
        articleServicePort.createArticle(name, text);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @RequestMapping(value = "/article/{name}", method = RequestMethod.GET)
    public ResponseEntity findArticleByName(@PathVariable String name) {
        Article article = articleServicePort.findArticleByName(name);
        return ResponseEntity.ok(article);
    }

}
