package com.baeldung.sample;

import com.thirdparty.sample.DBDriver;

public class SportsArticlePublisher implements ArticlePublisher {

	@Override
	public void createDatabaseEntries(Article article) throws PublishException {
		DBDriver.createNewRowForArticle(article.heading,  article.content);
		DBDriver.updateMetaData(article.content);
		
	}

	@Override
	public void publishArticle(Article article) throws PublishException {
		WebPublisher.publish(article);
	}

}
