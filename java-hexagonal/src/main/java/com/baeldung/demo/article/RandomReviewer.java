package com.baeldung.demo.article;

import com.baeldung.demo.article.entity.Article;

import com.baeldung.hexagonal.adapter.Adapter;
import com.baeldung.hexagonal.port.Port;

import com.baeldung.demo.article.ArticlePort;

import java.util.Random;


@Adapter(defaultPort = "com.baeldung.demo.article.ArticlePortImpl")
public class RandomReviewer {
    
    @Port
    ArticlePort port;
    
    private Random reader = new Random();
    
    protected boolean readAndGrade(Article article){
        int grade = reader.nextInt();
        return  grade >= 0 && ( grade % 101 ) >= 60;
    }
    
    public void review(Article article){
        System.out.format("RandomReviewer reviewing title='%s'%n", article.getTitle());
        if ( readAndGrade(article) ){
            System.out.format("RandomReviewer accepting to port title='%s'%n", article.getTitle());
            port.accept(article);
        }
        else {
            System.out.format("RandomReviewer rejecting to port title='%s'%n", article.getTitle());
            port.reject(article);
        }
        
    }
    
}