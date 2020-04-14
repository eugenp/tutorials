package com.baeldung.sample;

public interface ArticlePublisher {
	public void createDatabaseEntries(Article article);
	public void publishArticle(Article article);
}
