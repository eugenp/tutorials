package com.baeldung.sample.port;

public interface ArticleEvaluator {
	public void checkForFormattingCorrecctness(Article article);
	public void checkForContentCorrectness(Article article);
}
