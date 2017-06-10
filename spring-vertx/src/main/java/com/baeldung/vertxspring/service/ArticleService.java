package com.baeldung.vertxspring.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.vertxspring.entity.Article;
import com.baeldung.vertxspring.repository.ArticleRepository;

@Service
public class ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    public List<Article> getAllArticle() {
        List<Article> articles = new ArrayList<>();
        articleRepository.findAll()
            .forEach(articles::add);
        return articles;
    }

}
