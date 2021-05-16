package com.baeldung.optionalpathvars;

import static com.baeldung.optionalpathvars.Article.DEFAULT_ARTICLE;

import java.util.Optional;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;;

@RestController
@RequestMapping("/optionalParam")
public class ArticleViewerWithOptionalParamController {

    @RequestMapping(value = {"/article", "/article/{id}"})
    public Article getArticle(@PathVariable(name = "id") Optional<Integer> optionalArticleId) {

        if(optionalArticleId.isPresent()) {
            Integer articleId = optionalArticleId.get();
            return new Article(articleId);
        }else {
            return DEFAULT_ARTICLE;
        }
    }
    
}