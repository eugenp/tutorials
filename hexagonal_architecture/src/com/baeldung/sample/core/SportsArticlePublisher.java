package com.baeldung.sample.core;

import java.util.Date;

import com.baeldung.sample.adpater.DatabaseIntegrationAdapter;
import com.baeldung.sample.adpater.WebPublisher;
import com.baeldung.sample.port.Article;
import com.baeldung.sample.port.ArticlePublisher;

public class SportsArticlePublisher implements ArticlePublisher {
	DatabaseIntegrationAdapter dbAdapter;

	public SportsArticlePublisher() {
		dbAdapter = new DatabaseIntegrationAdapter();
	}	

	@Override
	public void createDatabaseEntries(Article article) {
		dbAdapter.insertArticleDataInDB(article.getHeading(), article.getContent());
		dbAdapter.insertArticleMetadataInDB(article.getHeading(), new Date());

	}

	@Override
	public void publishArticle(Article article) {
		WebPublisher.publish(article);
	}

}
