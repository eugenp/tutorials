package com.baeldung.spliteratorAPI;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Executor {

	public static int countAutors(Stream<Author> stream) {
		RelatedAuthorCounter wordCounter = stream.reduce(new RelatedAuthorCounter(0, true),
				RelatedAuthorCounter::accumulate, RelatedAuthorCounter::combine);
		return wordCounter.getCounter();
	}

	public static List<Article> generateElements() {
		return Stream.generate(() -> new Article("Java")).limit(35000).collect(Collectors.toList());
	}

}