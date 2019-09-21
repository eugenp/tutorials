package com.baeldung.hexagonal.dataacess.interfaces;

import com.baeldung.hexagonal.dataccess.entities.ArticleModel;

public interface IArticleRepositoryPort {
	ArticleModel getArticle(Integer articleId); 
}