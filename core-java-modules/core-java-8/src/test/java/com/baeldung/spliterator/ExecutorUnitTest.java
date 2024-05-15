package com.baeldung.spliterator;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Before;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExecutorUnitTest {
    Article article;

    @Before
    public void init() {
        article = new Article(Arrays.asList(new Author("Ahmad", 0), new Author("Eugen", 0), new Author("Alice", 1), new Author("Alice", 1), new Author("Mike", 0), new Author("Alice", 1), new Author("Mike", 0), new Author("Alice", 1), new Author("Mike", 0),
            new Author("Alice", 1), new Author("Mike", 0), new Author("Mike", 0), new Author("Alice", 1), new Author("Mike", 0), new Author("Alice", 1), new Author("Mike", 0), new Author("Alice", 1), new Author("Mike", 0), new Author("Alice", 1),
            new Author("Mike", 0), new Author("Michał", 0), new Author("Loredana", 1)), 0);
    }

    @Test
    public void givenAStreamOfIntegers_whenProcessedSequentialCustomSpliterator_countProducesRightOutput() {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        numbers.add(5);

        CustomSpliterator customSpliterator = new CustomSpliterator(numbers);

        AtomicInteger sum = new AtomicInteger();

        customSpliterator.forEachRemaining(sum::addAndGet);
        assertThat(sum.get()).isEqualTo(15);
    }

    @Test
    public void givenAStreamOfIntegers_whenProcessedInParallelWithCustomSpliterator_countProducesRightOutput() {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        numbers.add(5);

        CustomSpliterator customSpliterator = new CustomSpliterator(numbers);

        // Create a ForkJoinPool for parallel processing
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

        AtomicInteger sum = new AtomicInteger(0);

        // Process elements in parallel using parallelStream
        forkJoinPool.submit(() -> customSpliterator.forEachRemaining(sum::addAndGet)).join();
        assertThat(sum.get()).isEqualTo(15);
    }

    @Test
    public void givenAStreamOfArticles_whenProcessedSequentiallyWithSpliterator_ProducessRightOutput() {
        List<Article> articles = Stream.generate(() -> new Article("Java"))
            .limit(35000)
            .collect(Collectors.toList());

        Spliterator<Article> spliterator = articles.spliterator();
        while(spliterator.tryAdvance(article -> article.setName(article.getName().concat("- published by Baeldung"))));

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
