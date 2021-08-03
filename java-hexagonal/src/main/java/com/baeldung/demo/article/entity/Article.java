package com.baeldung.demo.article.entity;
public class Article{
    private String title;
    public String getTitle(){
        return this.title;
    }
    public Article(String title) {
        this.title = title; 
    }
}