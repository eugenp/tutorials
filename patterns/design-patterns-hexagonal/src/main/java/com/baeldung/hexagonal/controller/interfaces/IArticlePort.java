package com.baeldung.hexagonal.controller.interfaces;

import com.baeldung.hexagonal.dataccess.entities.ArticleModel;

public interface IArticlePort {
	public ArticleModel getArticle(Integer articleId);
}