package com.baeldung.spliterator;

import java.util.stream.Stream;

public class Executor {

	public static int countAuthors(Stream<Author> stream, int relatedArticleId) {
		RelatedAuthorCounter wordCounter = stream.reduce(new RelatedAuthorCounter(0, relatedArticleId),
				RelatedAuthorCounter::accumulate, RelatedAuthorCounter::combine);
		return wordCounter.getCounter();
	}
}