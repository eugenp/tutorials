package com.baeldung.demo.hexagonal.dataacess.interfaces;

import com.baeldung.demo.hexagonal.dataccess.model.ArticleModel;

public interface IArticleRepositoryPort {
    ArticleModel getArticle(Integer articleId);	
}