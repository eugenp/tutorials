package com.baeldung.hexagonal.dataacess.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baeldung.hexagonal.dataccess.entities.ArticleModel;

@Service
public class ArticleService {
	@Autowired
    private ArticleServiceAdapter articleRepository;

    public ArticleModel getArticle(Integer articleId){
        return articleRepository.getArticle(articleId);
    }
}