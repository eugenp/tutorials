package com.baeldung.demo;

import com.baeldung.hexagonal.core.boot.Application;
import com.baeldung.hexagonal.config.ApplicationStarter;

import com.baeldung.demo.article.entity.Article;
import com.baeldung.demo.article.RandomReviewer;

import java.util.HashMap;


@Application
public class HexagonalDemo {
    public static void entryPoint(ApplicationStarter starter, String... args){
       System.out.println("*** HexagonalDemo::entryPoint running ****");
       RandomReviewer reviewer = starter.getInstance(RandomReviewer.class);
       for (int i = 0; i < 6; i++){
           Article article = new Article(String.format("%d) Hexagonal Again!", i));
           reviewer.review(article);
       }
       
    }
    public static void main(String[] args) {
        System.out.println("*** HexagonalDemo running ****");
        // Configure Injection Mappings
        HashMap<String, String> mappings = new HashMap<>();
        mappings.put("com.baeldung.demo.article.RandomReviewer",
                     "com.baeldung.demo.article.ArticlePortImpl");
                     
        ApplicationStarter starter = ApplicationStarter.start(HexagonalDemo.class, mappings);
        entryPoint(starter, args);
    }

}
