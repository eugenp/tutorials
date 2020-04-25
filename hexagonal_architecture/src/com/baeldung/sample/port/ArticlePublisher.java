package com.baeldung.sample.port;

public interface ArticlePublisher {
	public void createDatabaseEntries(Article article);
	public void publishArticle(Article article);
}
