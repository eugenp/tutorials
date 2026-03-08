package com.baeldung.spring.jms;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
class ArticleListener {

	@Getter
	private List<ArticlePublisher.Article> receivedArticles = new CopyOnWriteArrayList<>();

	@JmsListener(destination = "articles-queue")
	void onArticleReceived(ArticlePublisher.Article article) {
		log.info("Received article: {}", article);
		receivedArticles.add(article);
	}
}
