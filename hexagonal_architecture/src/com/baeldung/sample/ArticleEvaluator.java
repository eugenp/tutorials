package com.baeldung.sample;

import com.baeldung.sample.Article;

public interface ArticleEvaluator {
	public void checkForFormattingCorrecctness(Article article) throws EvaluationException;
	public void checkForContentCorrectness(Article article) throws EvaluationException;
}
