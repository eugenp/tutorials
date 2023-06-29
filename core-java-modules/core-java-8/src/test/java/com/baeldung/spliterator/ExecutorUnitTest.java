package com.baeldung.spliterator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    List<Author> authorList;

    @Before
    public void init() {
        article = new Article(Arrays.asList(new Author("Ahmad", 0), new Author("Eugen", 0), new Author("Alice", 1), new Author("Alice", 1), new Author("Mike", 0), new Author("Alice", 1), new Author("Mike", 0), new Author("Alice", 1), new Author("Mike", 0),
            new Author("Alice", 1), new Author("Mike", 0), new Author("Mike", 0), new Author("Alice", 1), new Author("Mike", 0), new Author("Alice", 1), new Author("Mike", 0), new Author("Alice", 1), new Author("Mike", 0), new Author("Alice", 1),
            new Author("Mike", 0), new Author("Michał", 0), new Author("Loredana", 1)), 0);

        //spliterator = new RelatedAuthorSpliterator(article.getListOfAuthors());

        authorList = new ArrayList<>();

        for(int i= 0; i<1; i++) {
            authorList.add(new Author("bipin", 1));
            authorList.add(new Author("dhawal", 1));
            authorList.add(new Author("eugen", 1));
            authorList.add(new Author("john", 1));

            authorList.add(new Author("mike", 2));
            authorList.add(new Author("michal", 2));
            authorList.add(new Author("alice", 2));
            authorList.add(new Author("loredana", 2));

            authorList.add(new Author("praveen", 3));
            authorList.add(new Author("arjun", 3));
            authorList.add(new Author("kapil", 3));
        }
    }


    @Test
    public void authorBatchDivided(){


        //Map<Integer, Integer> totalNoOArticles = StreamSupport.stream(new RelatedAuthorSpliterator(authorList), true).collect(new RelatedAuthorBatchCount());

        Map<Integer, Integer> totalNoOArticles = authorList.stream().parallel().collect(new RelatedAuthorBatchCount());

        Set<Map.Entry<Integer,Integer>> entrySet = totalNoOArticles.entrySet();
        assertEquals(entrySet.size(), 3);

        for(Map.Entry<Integer,Integer> total : totalNoOArticles.entrySet()){
            if(total.getKey() == 1){
                System.out.println(total.getKey() + " - " + total.getValue());
            }
            else if(total.getKey() == 2){
                System.out.println(total.getKey() + " - " + total.getValue());
            }
            else{
                System.out.println(total.getKey() + " - " + total.getValue());
            }
        }
    }
    @Test
    public void givenAstreamOfAuthors_whenProcessedInParallelWithCustomSpliterator_coubtProducessRightOutput() {
        Stream<Author> stream2 = StreamSupport.stream(spliterator, true);
        //assertThat(Executor.countAutors(stream2.parallel())).isEqualTo(9);
        System.out.println(Executor.countAutors(stream2.parallel()));
    }

    @Test
    public void givenAStreamOfAuthors_whenProcessedInParallel_countProducesWrongOutput() {
        Stream<Author> stream = article.getListOfAuthors().stream();
        System.out.println(Executor.countAutors(stream.parallel()));
       // assertThat(Executor.countAutors(stream.parallel())).isGreaterThan(9);
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
