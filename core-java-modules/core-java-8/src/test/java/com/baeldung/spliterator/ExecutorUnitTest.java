package com.baeldung.spliterator;

import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.*;
import org.junit.Before;
import org.junit.Test;

import com.baeldung.spliterator.Article;
import com.baeldung.spliterator.Author;
import com.baeldung.spliterator.Executor;
import com.baeldung.spliterator.RelatedAuthorSpliterator;
import com.baeldung.spliterator.Task;

public class ExecutorUnitTest {
    Article article;
    Stream<Author> stream;
    Spliterator<Author> spliterator;
    Spliterator<Article> split1;
    Spliterator<Article> split2;

    @Before
    public void init() {
        article = new Article(Arrays.asList(new Author("Ahmad", 0), new Author("Eugen", 0), new Author("Alice", 1), new Author("Alice", 1), new Author("Mike", 0), new Author("Alice", 1), new Author("Mike", 0), new Author("Alice", 1), new Author("Mike", 0),
            new Author("Alice", 1), new Author("Mike", 0), new Author("Mike", 0), new Author("Alice", 1), new Author("Mike", 0), new Author("Alice", 1), new Author("Mike", 0), new Author("Alice", 1), new Author("Mike", 0), new Author("Alice", 1),
            new Author("Mike", 0), new Author("Michał", 0), new Author("Loredana", 1)), 0);
        stream = article.getListOfAuthors()
            .stream();
        split1 = Executor.generateElements()
            .spliterator();
        split2 = split1.trySplit();
        spliterator = new RelatedAuthorSpliterator(article.getListOfAuthors());
    }

    @Test
    public void givenAstreamOfAuthors_whenProcessedInParallelWithCustomSpliterator_coubtProducessRightOutput() {
        Stream<Author> stream2 = StreamSupport.stream(spliterator, true);
        assertThat(Executor.countAutors(stream2.parallel())).isEqualTo(9);
    }

    @Test
    public void givenSpliterator_whenAppliedToAListOfArticle_thenSplittedInHalf() {
        assertThat(new Task(split1).call()).containsSequence(Executor.generateElements()
            .size() / 2 + "");
        assertThat(new Task(split2).call()).containsSequence(Executor.generateElements()
            .size() / 2 + "");
    }

    @Test
    public void givenAstreamOfArticles_whenProcessedInSequentiallyWithSpliterator_ProducessRightOutput() {
        List<Article> articles = Stream.generate(() -> new Article("Java"))
            .limit(35000)
            .collect(Collectors.toList());

        Spliterator<Article> spliterator = articles.spliterator();
        while (spliterator.tryAdvance(article -> article.setName(article.getName()
            .concat("- published by Baeldung"))));

        articles.forEach(article -> assertThat(article.getName()).isEqualTo("Java- published by Baeldung"));
    }
}
