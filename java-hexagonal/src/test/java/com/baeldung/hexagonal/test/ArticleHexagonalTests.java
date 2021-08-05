package com.baeldung.hexagonal.test;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.junit.Assert.*;  

import com.baeldung.hexagonal.core.boot.Application;
import com.baeldung.hexagonal.config.ApplicationStarter;


import com.baeldung.hexagonal.port.Port;
import com.baeldung.hexagonal.adapter.Adapter;

import java.util.HashMap;


@Application
class HexagonalTestMain {
    public static class ArticlePortImpl implements ArticlePort{
    }
    public static void main(String... args){
    }
}
class Article{
    private String title;
    private String content;
    
    public String getTitle(){
        return this.title;
    }
    public String getContent(){
        return this.content;
    }
    public Article(String title, String content) {
        this.title = title; 
        this.content = content;
    }
    public Article(String title) {
        this(title, "");
    }
}
interface ArticlePort{
    default public void accept(Article article){
        
    }
    default public void reject(Article article){
        
    }
}

@Adapter(defaultPort= "com.baeldung.hexagonal.test.HexagonalTestMain$ArticlePortImpl")
class JavaReviewer {
    @Port
    ArticlePort port;
    final private String keyword = "^.*[Jj]ava(script)?.*$";
    
    private int rejects = 0;
    public int getRejects(){
        return this.rejects;
    }
    
    public void review(Article article){
        if ( article.getTitle().matches(this.keyword) )
            port.accept(article);
        else {
            port.reject(article);
            this.rejects++;
        }
    }
}


public class ArticleHexagonalTests {
    ApplicationStarter starter;
    
    @Before
    public void setup(){
       HashMap<String, String> mappings = new HashMap<>();
       this.starter = ApplicationStarter.start(HexagonalTestMain.class, mappings);
    }

	@Test 
	public void JavaReviewerTest_When_ZeroRejects() {
       try{ 
           JavaReviewer reviewer = this.starter.getInstance(JavaReviewer.class);
           Article article = new Article("Java is verbose");
           reviewer.review(article);
           article =  new Article("C++ is heavy"); 
           reviewer.review(article);
           assertTrue(1 == reviewer.getRejects());
       } catch (Exception e){
           fail(e.getMessage());
       }
	}
    @After
    public void cleanup(){
        this.starter = null;
    }

}
