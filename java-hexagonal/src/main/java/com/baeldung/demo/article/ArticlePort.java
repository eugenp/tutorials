package com.baeldung.demo.article;

import com.baeldung.demo.article.entity.Article;
import com.baeldung.hexagonal.port.Port;

public interface ArticlePort{
    default public void accept(Article article){
        
    }
    default public void reject(Article article){
        return;
    }
}
