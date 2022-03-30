package com.baeldung.springboot.swagger.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baeldung.springboot.swagger.model.Article;

@Service
public class ArticleService {
	
	private List<Article> articles = new ArrayList<>();
	
	public List<Article> getAllArticles(){
		return articles;
	}
	
	public void addArticle(Article article) {
		article.setId(articles.size()+1);
		articles.add(article);
	}
	

}
