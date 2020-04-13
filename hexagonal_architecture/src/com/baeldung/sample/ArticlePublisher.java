package com.baeldung.sample;

import com.baeldung.sample.Article;

public interface ArticlePublisher {
	public void createDatabaseEntries(Article article) throws PublishException;
	public void publishArticle(Article article)  throws PublishException;
}
