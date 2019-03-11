package com.baeldung.hexagonal.framework_layer.primary.adapters;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonal.core.domain.Article;
import com.baeldung.hexagonal.framework_layer.primary.ports.ArticleControllerGUIPort;

@RestController
public class ArticleControllerGUIAdapter implements ArticleControllerGUIPort {

    @PostMapping(value = "/article")
    @ResponseBody
    @Override
    public ResponseEntity<?> createArticle(@RequestBody Article article) {

        return new ResponseEntity<>("article created", HttpStatus.CREATED);
    }

    @GetMapping(value = "/article/{articleId}")
    @ResponseBody
    @Override
    public ResponseEntity<?> getArticleById(@PathVariable String articleId) {
        return new ResponseEntity<>("article retrieved", HttpStatus.OK);
    }

    @DeleteMapping(value = "/article/{articleId}")
    @ResponseBody
    @Override
    public ResponseEntity<?> deleteArticleById(@PathVariable String articleId) {
        return new ResponseEntity<>("article deleted", HttpStatus.OK);
    }

    @PutMapping(value = "/article/{articleId}")
    @ResponseBody
    @Override
    public ResponseEntity<?> updateArticle(@PathVariable String articleId, @RequestBody Article article) {
        return new ResponseEntity<>("article updated", HttpStatus.OK);
    }

}
