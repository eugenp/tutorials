package com.baeldung.sample;

import com.thirdparty.sample.PlagarismChecker;

public class SportsArticleEvaluator implements ArticleEvaluator {

	private static final String HEADING_SIZE = "H2";

	@Override
	public void checkForFormattingCorrecctness(Article article) throws EvaluationException {
		if(article.heading.size!=HEADING_SIZE) {
			throw new EvaluationException("Incorrect Heading Size");
		}
		
		if(article.content.length()<=300) {
			throw new EvaluationException("Too small content, please add more details");
		}
			
	}

	@Override
	public void checkForContentCorrectness(Article article) throws EvaluationException{
		PlagarismChecker.checkOnlineForPlagirism(article.content);
		
		PlagarismChecker.checkOnlineForFactCheck(article.content);
	}

}
