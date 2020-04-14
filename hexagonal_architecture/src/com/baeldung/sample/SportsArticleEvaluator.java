package com.baeldung.sample;

import com.thirdparty.sample.PlagiarismChecker;

public class SportsArticleEvaluator implements ArticleEvaluator {

	private static final String HEADING_SIZE = "H2";

	@Override
	public void checkForFormattingCorrecctness(Article article) {
		if(article.heading.size!=HEADING_SIZE) {
			throw new RuntimeException("Incorrect Heading Size");
		}
		
		if(article.content.length()<=300) {
			throw new RuntimeException("Too small content, please add more details");
		}
	}

	@Override
	public void checkForContentCorrectness(Article article){
		double plagiarismPercentage = PlagiarismChecker.checkOnlineForPlagiarism(article.content);
		
		if(plagiarismPercentage >= 25.00) {
			throw new RuntimeException("Plagiarism check failed!");
		}
		
		double confidence = PlagiarismChecker.checkOnlineForFactCheck(article.content);
		if(confidence < 80.00) {
			throw new RuntimeException("Fact check failed!, please re-check the score details mentioned in article");
		}
	}

}
