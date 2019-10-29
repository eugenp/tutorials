package com.baeldung.web.controller.optionalpathvars;

import static com.baeldung.model.Article.DEFAULT_ARTICLE;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.model.Article;

@RestController
@RequestMapping(value = "/seperateMethods")
public class ArticleViewerWithTwoSeparateMethodsController {

    @RequestMapping(value = "/articles/{id}")
    public Article getArticle(@PathVariable(name = "id") Integer articleId) {
        
        return new Article(articleId);        
    }
    
    @RequestMapping(value = "/articless/{id}")
    public Article getArticle3() {
        
        return new Article(5);        
    }
    
    @RequestMapping(value = "/articles")
    public Article getDefaultArticle() {

        return DEFAULT_ARTICLE;
    }

}