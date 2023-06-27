package com.baeldung.spliterator;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.junit.Before;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExecutorUnitTest {
    Article article;
    Stream<Author> stream;
    Spliterator<Author> spliterator;

    @Before
    public void init() {
        article = new Article(Arrays.asList(new Author("Ahmad", 0), new Author("Eugen", 0), new Author("Alice", 1), new Author("Alice", 1), new Author("Mike", 0), new Author("Alice", 1), new Author("Mike", 0), new Author("Alice", 1), new Author("Mike", 0),
            new Author("Alice", 1), new Author("Mike", 0), new Author("Mike", 0), new Author("Alice", 1), new Author("Mike", 0), new Author("Alice", 1), new Author("Mike", 0), new Author("Alice", 1), new Author("Mike", 0), new Author("Alice", 1),
            new Author("Mike", 0), new Author("Michał", 0), new Author("Loredana", 1)), 0);

        spliterator = new RelatedAuthorSpliterator(article.getListOfAuthors());
    }

    @Test
    public void givenAstreamOfAuthors_whenProcessedInParallelWithCustomSpliterator_coubtProducessRightOutput() {
        Stream<Author> stream2 = StreamSupport.stream(spliterator, true);
        assertThat(Executor.countAutors(stream2.parallel())).isEqualTo(9);
    }

    @Test
    public void givenAStreamOfArticles_whenProcessedSequentiallyWithSpliterator_ProducessRightOutput() {
        List<Article> articles = Stream.generate(() -> new Article("Java"))
            .limit(35000)
            .collect(Collectors.toList());

        Spliterator<Article> spliterator = articles.spliterator();
        while (spliterator.tryAdvance(article -> article.setName(article.getName()
            .concat("- published by Baeldung"))))
            ;

        articles.forEach(article -> assertThat(article.getName()).isEqualTo("Java- published by Baeldung"));
    }

    @Test
    public void givenAStreamOfArticle_whenProcessedUsingTrySplit_thenSplitIntoEqualHalf() {
        List<Article> articles = Stream.generate(() -> new Article("Java"))
            .limit(35000)
            .collect(Collectors.toList());

        Spliterator<Article> split1 = articles.spliterator();
        Spliterator<Article> split2 = split1.trySplit();

        log.info("Size: " + split1.estimateSize());
        log.info("Characteristics: " + split1.characteristics());

        List<Article> articlesListOne = new ArrayList<>();
        List<Article> articlesListTwo = new ArrayList<>();

        split1.forEachRemaining(articlesListOne::add);
        split2.forEachRemaining(articlesListTwo::add);

        assertThat(articlesListOne.size()).isEqualTo(17500);
        assertThat(articlesListTwo.size()).isEqualTo(17500);

        assertThat(articlesListOne).doesNotContainAnyElementsOf(articlesListTwo);
    }
}
