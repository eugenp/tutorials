package com.baeldung.sample;

import com.thirdparty.sample.DBDriver;

public class SportsArticlePublisher implements ArticlePublisher {

	@Override
	public void createDatabaseEntries(Article article) {
		DBDriver.createNewRowForArticle(article.heading.text,  article.content);
		DBDriver.updateMetaData(article.content);
		
	}

	@Override
	public void publishArticle(Article article) {
		WebPublisher.publish(article);
	}

}
