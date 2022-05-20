package splitting;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;
import rx.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamSplittingUnitTest {
    // given
    private final static List<Article> articles = Lists.newArrayList(
            new Article("Baeldung", true),
            new Article("Baeldung", false),
            new Article("Programming Daily", false),
            new Article("The Code", false)
    );

    @Test
    public void givenListOfArticles_shouldSplitListInTwoCategories_usingPartitioningBy() {
        // when
        Map<Boolean, List<Article>> groupedArticles = articles.stream().collect(Collectors.partitioningBy(a -> a.target.equals("Baeldung")));

        // then
        Assert.assertEquals(2, groupedArticles.get(true).size());
        Assert.assertEquals(2, groupedArticles.get(false).size());
    }

    @Test
    public void givenListOfArticles_shouldSplitListInMultipleCategories_usingGroupingBy() {
        // when
        Map<String, List<Article>> groupedArticles = articles.stream().collect(Collectors.groupingBy(a -> a.target));

        // then
        Assert.assertEquals(2, groupedArticles.get("Baeldung").size());
        Assert.assertEquals(1, groupedArticles.get("Programming Daily").size());
        Assert.assertEquals(1, groupedArticles.get("The Code").size());
    }

    @Test
    public void givenListOfArticles_shouldSplitListInTwoCategories_usingTeeing() {
        // when
        List<Long> countedArticles = articles.stream().collect(Collectors.teeing(
                Collectors.filtering(article -> article.target.equals("Baeldung"), Collectors.counting()),
                Collectors.filtering(article -> !article.target.equals("Baeldung"), Collectors.counting()),
                List::of));

        // then
        Assert.assertEquals(2, (long) countedArticles.get(0));
        Assert.assertEquals(2, (long) countedArticles.get(1));
    }

    @Test
    public void givenListOfArticles_shouldSplitListInTwoOVerlappingCategories_usingTeeing() {
        // when
        List<List<Article>> groupedArticles = articles.stream().collect(Collectors.teeing(
                Collectors.filtering(article -> article.target.equals("Baeldung"), Collectors.toList()),
                Collectors.filtering(article -> article.featured, Collectors.toList()),
                List::of));

        // then
        Assert.assertEquals(2, groupedArticles.get(0).size());
        Assert.assertEquals(1, groupedArticles.get(1).size());
    }

    @Test
    public void givenListOfArticles_shouldSplitStreamInMultipleCategories_usingRxJava() {
        // given
        Observable<Article> observableArticles = Observable.from(articles);

        // when
        Observable<Article> baeldungObservable = observableArticles.filter(article -> article.target.equals("Baeldung"));
        Observable<Article> featuredObservable = observableArticles.filter(article -> article.featured);
        List<Article> baeldungArticles = new ArrayList<>();
        List<Article> featuredArticles = new ArrayList<>();
        baeldungObservable.subscribe(baeldungArticles::add);
        featuredObservable.subscribe(featuredArticles::add);

        // then
        Assert.assertEquals(2, baeldungArticles.size());
        Assert.assertEquals(1, featuredArticles.size());
    }
}
