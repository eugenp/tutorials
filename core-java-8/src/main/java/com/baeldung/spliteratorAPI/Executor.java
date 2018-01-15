package com.baeldung.spliteratorAPI;

import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Executor {
	public void executeCustomSpliterator() {
        Article article = new Article(Arrays.asList(new Author("Ahmad", 0), new Author("Eugen", 0), new Author("Alice", 1), new Author("Alice", 1), new Author("Mike", 0), new Author("Alice", 1), new Author("Mike", 0), new Author("Alice", 1),
            new Author("Mike", 0), new Author("Alice", 1), new Author("Mike", 0), new Author("Mike", 0), new Author("Alice", 1), new Author("Mike", 0), new Author("Alice", 1), new Author("Mike", 0), new Author("Alice", 1), new Author("Mike", 0),
            new Author("Alice", 1), new Author("Mike", 0), new Author("Michał", 0), new Author("Loredana", 1)), 0);
        Stream<Author> stream = IntStream.range(0, article.getListOfAuthors()
            .size())
            .mapToObj(article.getListOfAuthors()::get);
        System.out.println("count= " + countAutors(stream.parallel()));
        Spliterator<Author> spliterator = new RelatedAuthorSpliterator(article.getListOfAuthors());
        Stream<Author> stream2 = StreamSupport.stream(spliterator, true);
        System.out.println("count= " + countAutors(stream2.parallel()));
    }

    public void executeSpliterator() {
        Spliterator<Article> split1 = generateElements().spliterator();
        Spliterator<Article> split2 = split1.trySplit();
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(new Task(split1));
        service.execute(new Task(split2));
    }

    private static int countAutors(Stream<Author> stream) {
        RelatedAuthorCounter wordCounter = stream.reduce(new RelatedAuthorCounter(0, true), RelatedAuthorCounter::accumulate, RelatedAuthorCounter::combine);
        return wordCounter.getCounter();
    }

    private List<Article> generateElements() {
        return Stream.generate(() -> new Article("Java"))
            .limit(35000)
            .collect(Collectors.toList());
    }
}
