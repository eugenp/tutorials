package com.baeldung.optionalpathvars;

import static com.baeldung.optionalpathvars.Article.DEFAULT_ARTICLE;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleViewerController {

    @RequestMapping(value = {"/article", "/article/{id}"})
    public Article getArticle(@PathVariable(name = "id") Integer articleId) {

        if (articleId != null) {
            return new Article(articleId);
        } else {
            return DEFAULT_ARTICLE;
        }

    }

}