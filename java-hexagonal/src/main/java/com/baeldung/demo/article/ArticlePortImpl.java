package com.baeldung.demo.article;

import com.baeldung.demo.article.entity.Article;
import com.baeldung.hexagonal.port.Port;


public class ArticlePortImpl implements ArticlePort{
    @Override
    public void reject(Article article){
        System.out.format(">>>ArticlePortImpl rejecting article title='%s'%n", article.getTitle()); 
        return;
    }
}
