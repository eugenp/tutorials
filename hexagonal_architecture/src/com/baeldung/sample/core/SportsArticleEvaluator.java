package com.baeldung.sample.core;

import com.baeldung.sample.adpater.WebPublisher;
import com.baeldung.sample.port.Article;
import com.baeldung.sample.port.ArticleEvaluator;

public class SportsArticleEvaluator implements ArticleEvaluator {

	private static final String HEADING_SIZE = "H2";

	@Override
	public void checkForFormattingCorrecctness(Article article) {
		if(article.getHeadingSize()!=HEADING_SIZE) {
			throw new RuntimeException("Incorrect Heading Size");
		}
		
		if(article.getContent().length()<=300) {
			throw new RuntimeException("Too small content, please add more details");
		}
	}

	@Override
	public void checkForContentCorrectness(Article article){
		double plagiarismPercentage = WebPublisher.checkOnlineForPlagiarismScore(article.content);
		
		if(plagiarismPercentage >= 25.00) {
			throw new RuntimeException("Plagiarism check failed!");
		}
		
		double confidence = WebPublisher.checkOnlineForFactScore(article.content);
		if(confidence < 80.00) {
			throw new RuntimeException("Fact check failed!, please re-check the score details mentioned in article");
		}
	}

}
