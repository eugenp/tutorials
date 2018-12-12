package org.baeldung.hexagonal.adapter;

import org.baeldung.hexagonal.news.Article;

public interface ArticleDeliverer {
	
	public void deliverArticle(Article article);
	
}
