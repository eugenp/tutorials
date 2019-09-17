package com.baeldung.java_8_features.groupingby;

import com.baeldung.java_8_features.groupingby.BlogPost;
import com.baeldung.java_8_features.groupingby.BlogPostType;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentMap;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;
import static org.junit.Assert.*;

public class Java8GroupingByCollectorUnitTest {

    private static final List<BlogPost> posts = Arrays.asList(new BlogPost("News item 1", "Author 1", BlogPostType.NEWS, 15), new BlogPost("Tech review 1", "Author 2", BlogPostType.REVIEW, 5),
        new BlogPost("Programming guide", "Author 1", BlogPostType.GUIDE, 20), new BlogPost("News item 2", "Author 2", BlogPostType.NEWS, 35), new BlogPost("Tech review 2", "Author 1", BlogPostType.REVIEW, 15));

    @Test
    public void givenAListOfPosts_whenGroupedByType_thenGetAMapBetweenTypeAndPosts() {
        Map<BlogPostType, List<BlogPost>> postsPerType = posts.stream()
            .collect(groupingBy(BlogPost::getType));

        assertEquals(2, postsPerType.get(BlogPostType.NEWS)
            .size());
        assertEquals(1, postsPerType.get(BlogPostType.GUIDE)
            .size());
        assertEquals(2, postsPerType.get(BlogPostType.REVIEW)
            .size());
    }

    @Test
    public void givenAListOfPosts_whenGroupedByTypeAndTheirTitlesAreJoinedInAString_thenGetAMapBetweenTypeAndCsvTitles() {
        Map<BlogPostType, String> postsPerType = posts.stream()
            .collect(groupingBy(BlogPost::getType, mapping(BlogPost::getTitle, joining(", ", "Post titles: [", "]"))));

        assertEquals("Post titles: [News item 1, News item 2]", postsPerType.get(BlogPostType.NEWS));
        assertEquals("Post titles: [Programming guide]", postsPerType.get(BlogPostType.GUIDE));
        assertEquals("Post titles: [Tech review 1, Tech review 2]", postsPerType.get(BlogPostType.REVIEW));
    }

    @Test
    public void givenAListOfPosts_whenGroupedByTypeAndSumTheLikes_thenGetAMapBetweenTypeAndPostLikes() {
        Map<BlogPostType, Integer> likesPerType = posts.stream()
            .collect(groupingBy(BlogPost::getType, summingInt(BlogPost::getLikes)));

        assertEquals(50, likesPerType.get(BlogPostType.NEWS)
            .intValue());
        assertEquals(20, likesPerType.get(BlogPostType.REVIEW)
            .intValue());
        assertEquals(20, likesPerType.get(BlogPostType.GUIDE)
            .intValue());
    }

    @Test
    public void givenAListOfPosts_whenGroupedByTypeInAnEnumMap_thenGetAnEnumMapBetweenTypeAndPosts() {
        EnumMap<BlogPostType, List<BlogPost>> postsPerType = posts.stream()
            .collect(groupingBy(BlogPost::getType, () -> new EnumMap<>(BlogPostType.class), toList()));

        assertEquals(2, postsPerType.get(BlogPostType.NEWS)
            .size());
        assertEquals(1, postsPerType.get(BlogPostType.GUIDE)
            .size());
        assertEquals(2, postsPerType.get(BlogPostType.REVIEW)
            .size());
    }

    @Test
    public void givenAListOfPosts_whenGroupedByTypeInSets_thenGetAMapBetweenTypesAndSetsOfPosts() {
        Map<BlogPostType, Set<BlogPost>> postsPerType = posts.stream()
            .collect(groupingBy(BlogPost::getType, toSet()));

        assertEquals(2, postsPerType.get(BlogPostType.NEWS)
            .size());
        assertEquals(1, postsPerType.get(BlogPostType.GUIDE)
            .size());
        assertEquals(2, postsPerType.get(BlogPostType.REVIEW)
            .size());
    }

    @Test
    public void givenAListOfPosts_whenGroupedByTypeConcurrently_thenGetAMapBetweenTypeAndPosts() {
        ConcurrentMap<BlogPostType, List<BlogPost>> postsPerType = posts.parallelStream()
            .collect(groupingByConcurrent(BlogPost::getType));

        assertEquals(2, postsPerType.get(BlogPostType.NEWS)
            .size());
        assertEquals(1, postsPerType.get(BlogPostType.GUIDE)
            .size());
        assertEquals(2, postsPerType.get(BlogPostType.REVIEW)
            .size());
    }

    @Test
    public void givenAListOfPosts_whenGroupedByTypeAndAveragingLikes_thenGetAMapBetweenTypeAndAverageNumberOfLikes() {
        Map<BlogPostType, Double> averageLikesPerType = posts.stream()
            .collect(groupingBy(BlogPost::getType, averagingInt(BlogPost::getLikes)));

        assertEquals(25, averageLikesPerType.get(BlogPostType.NEWS)
            .intValue());
        assertEquals(20, averageLikesPerType.get(BlogPostType.GUIDE)
            .intValue());
        assertEquals(10, averageLikesPerType.get(BlogPostType.REVIEW)
            .intValue());
    }

    @Test
    public void givenAListOfPosts_whenGroupedByTypeAndCounted_thenGetAMapBetweenTypeAndNumberOfPosts() {
        Map<BlogPostType, Long> numberOfPostsPerType = posts.stream()
            .collect(groupingBy(BlogPost::getType, counting()));

        assertEquals(2, numberOfPostsPerType.get(BlogPostType.NEWS)
            .intValue());
        assertEquals(1, numberOfPostsPerType.get(BlogPostType.GUIDE)
            .intValue());
        assertEquals(2, numberOfPostsPerType.get(BlogPostType.REVIEW)
            .intValue());
    }

    @Test
    public void givenAListOfPosts_whenGroupedByTypeAndMaxingLikes_thenGetAMapBetweenTypeAndMaximumNumberOfLikes() {
        Map<BlogPostType, Optional<BlogPost>> maxLikesPerPostType = posts.stream()
            .collect(groupingBy(BlogPost::getType, maxBy(comparingInt(BlogPost::getLikes))));

        assertTrue(maxLikesPerPostType.get(BlogPostType.NEWS)
            .isPresent());
        assertEquals(35, maxLikesPerPostType.get(BlogPostType.NEWS)
            .get()
            .getLikes());

        assertTrue(maxLikesPerPostType.get(BlogPostType.GUIDE)
            .isPresent());
        assertEquals(20, maxLikesPerPostType.get(BlogPostType.GUIDE)
            .get()
            .getLikes());

        assertTrue(maxLikesPerPostType.get(BlogPostType.REVIEW)
            .isPresent());
        assertEquals(15, maxLikesPerPostType.get(BlogPostType.REVIEW)
            .get()
            .getLikes());
    }

    @Test
    public void givenAListOfPosts_whenGroupedByAuthorAndThenByType_thenGetAMapBetweenAuthorAndMapsBetweenTypeAndBlogPosts() {
        Map<String, Map<BlogPostType, List<BlogPost>>> map = posts.stream()
            .collect(groupingBy(BlogPost::getAuthor, groupingBy(BlogPost::getType)));

        assertEquals(1, map.get("Author 1")
            .get(BlogPostType.NEWS)
            .size());
        assertEquals(1, map.get("Author 1")
            .get(BlogPostType.GUIDE)
            .size());
        assertEquals(1, map.get("Author 1")
            .get(BlogPostType.REVIEW)
            .size());

        assertEquals(1, map.get("Author 2")
            .get(BlogPostType.NEWS)
            .size());
        assertEquals(1, map.get("Author 2")
            .get(BlogPostType.REVIEW)
            .size());
        assertNull(map.get("Author 2")
            .get(BlogPostType.GUIDE));
    }

    @Test
    public void givenAListOfPosts_whenGroupedByTypeAndSummarizingLikes_thenGetAMapBetweenTypeAndSummary() {
        Map<BlogPostType, IntSummaryStatistics> likeStatisticsPerType = posts.stream()
            .collect(groupingBy(BlogPost::getType, summarizingInt(BlogPost::getLikes)));

        IntSummaryStatistics newsLikeStatistics = likeStatisticsPerType.get(BlogPostType.NEWS);

        assertEquals(2, newsLikeStatistics.getCount());
        assertEquals(50, newsLikeStatistics.getSum());
        assertEquals(25.0, newsLikeStatistics.getAverage(), 0.001);
        assertEquals(35, newsLikeStatistics.getMax());
        assertEquals(15, newsLikeStatistics.getMin());
    }

}
