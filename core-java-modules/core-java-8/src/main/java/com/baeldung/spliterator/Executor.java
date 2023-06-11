package com.baeldung.spliterator;

import java.util.stream.Stream;

public class Executor {

	public static int countAutors(Stream<Author> stream) {
		RelatedAuthorCounter wordCounter = stream.reduce(new RelatedAuthorCounter(0, true),
				RelatedAuthorCounter::accumulate, RelatedAuthorCounter::combine);
		return wordCounter.getCounter();
	}
}