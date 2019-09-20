package com.baeldung.demo.hexagonal.controller.interfaces;

import com.baeldung.demo.hexagonal.dataccess.model.ArticleModel;

public interface IArticlePort {

	public ArticleModel getArticle(Integer articleId);

}