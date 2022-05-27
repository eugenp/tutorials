package splitting;

import com.google.common.collect.Lists;
import org.junit.Test;
import rx.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StreamSplittingUnitTest {
    private final static List<Article> articles = Lists.newArrayList(
            new Article("Baeldung", true),
            new Article("Baeldung", false),
            new Article("Programming Daily", false),
            new Article("The Code", false)
    );

    @Test
    public void givenListOfArticles_shouldSplitListInTwoCategories_usingPartitioningBy() {
        Map<Boolean, List<Article>> groupedArticles = articles.stream().collect(Collectors.partitioningBy(a -> a.getTarget().equals("Baeldung")));

        assertTrue(groupedArticles.get(true).containsAll(Lists.newArrayList(
                new Article("Baeldung", true),
                new Article("Baeldung", false))));
        assertTrue(groupedArticles.get(false).containsAll(Lists.newArrayList(
                new Article("Programming Daily", false),
                new Article("The Code", false))));
    }

    @Test
    public void givenListOfArticles_shouldSplitListInMultipleCategories_usingGroupingBy() {
        Map<String, List<Article>> groupedArticles = articles.stream().collect(Collectors.groupingBy(Article::getTarget));

        assertEquals(2, groupedArticles.get("Baeldung").size());
        assertEquals(1, groupedArticles.get("Programming Daily").size());
        assertEquals(1, groupedArticles.get("The Code").size());

        assertTrue(groupedArticles.get("Baeldung").containsAll(Lists.newArrayList(
                new Article("Baeldung", true),
                new Article("Baeldung", false))));
        assertTrue(groupedArticles.get("Programming Daily").contains(new Article("Programming Daily", false)));
        assertTrue(groupedArticles.get("The Code").contains(new Article("The Code", false)));
    }

    @Test
    public void givenListOfArticles_shouldSplitListInTwoCategories_usingTeeing() {
        List<Long> countedArticles = articles.stream().collect(Collectors.teeing(
                Collectors.filtering(article -> article.getTarget().equals("Baeldung"), Collectors.counting()),
                Collectors.filtering(article -> !article.getTarget().equals("Baeldung"), Collectors.counting()),
                List::of));

        assertEquals(2, (long) countedArticles.get(0));
        assertEquals(2, (long) countedArticles.get(1));
    }

    @Test
    public void givenListOfArticles_shouldSplitListInTwoOVerlappingCategories_usingTeeing() {
        List<List<Article>> groupedArticles = articles.stream().collect(Collectors.teeing(
                Collectors.filtering(article -> article.getTarget().equals("Baeldung"), Collectors.toList()),
                Collectors.filtering(Article::isFeatured, Collectors.toList()),
                List::of));

        assertEquals(2, groupedArticles.get(0).size());
        assertEquals(1, groupedArticles.get(1).size());

        assertTrue(groupedArticles.get(0).containsAll(Lists.newArrayList(
                new Article("Baeldung", true),
                new Article("Baeldung", false))));
        assertTrue(groupedArticles.get(1).contains(new Article("Baeldung", true)));
    }

    @Test
    public void givenListOfArticles_shouldSplitStreamInMultipleCategories_usingRxJava() {
        Observable<Article> observableArticles = Observable.from(articles);

        Observable<Article> baeldungObservable = observableArticles.filter(article -> article.getTarget().equals("Baeldung"));
        Observable<Article> featuredObservable = observableArticles.filter(Article::isFeatured);
        List<Article> baeldungArticles = new ArrayList<>();
        List<Article> featuredArticles = new ArrayList<>();
        baeldungObservable.subscribe(baeldungArticles::add);
        featuredObservable.subscribe(featuredArticles::add);

        assertEquals(2, baeldungArticles.size());
        assertEquals(1, featuredArticles.size());

        assertTrue(baeldungArticles.containsAll(Lists.newArrayList(
                new Article("Baeldung", true),
                new Article("Baeldung", false))));
        assertTrue(featuredArticles.contains(new Article("Baeldung", true)));
    }
}
