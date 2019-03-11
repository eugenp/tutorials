package com.baeldung.hexagonal.framework_layer.primary.ports;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.baeldung.hexagonal.core.domain.Article;

public interface ArticleControllerGUIPort {

    public ResponseEntity<?> createArticle(@RequestBody Article article);

    public ResponseEntity<?> getArticleById(@PathVariable String articleId);

    public ResponseEntity<?> deleteArticleById(@PathVariable String articleId);

    public ResponseEntity<?> updateArticle(@PathVariable String articleId, @RequestBody Article article);

}
