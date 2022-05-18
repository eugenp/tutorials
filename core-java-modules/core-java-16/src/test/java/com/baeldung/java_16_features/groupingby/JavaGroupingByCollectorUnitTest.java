package com.baeldung.java_16_features.groupingby;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.averagingInt;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.groupingByConcurrent;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.summarizingInt;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

public class JavaGroupingByCollectorUnitTest {

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

    @Test
    public void givenAListOfPosts_whenGroupedByComplexMapPairKeyType_thenGetAMapBetweenPairAndList() {

        Map<Pair<BlogPostType, String>, List<BlogPost>> postsPerTypeAndAuthor = posts.stream()
            .collect(groupingBy(post -> new ImmutablePair<>(post.getType(), post.getAuthor())));

        List<BlogPost> result = postsPerTypeAndAuthor.get(new ImmutablePair<>(BlogPostType.GUIDE, "Author 1"));

        assertThat(result.size()).isEqualTo(1);

        BlogPost blogPost = result.get(0);

        assertThat(blogPost.getTitle()).isEqualTo("Programming guide");
        assertThat(blogPost.getType()).isEqualTo(BlogPostType.GUIDE);
        assertThat(blogPost.getAuthor()).isEqualTo("Author 1");
    }

    @Test
    public void givenAListOfPosts_whenGroupedByComplexMapKeyType_thenGetAMapBetweenTupleAndList() {

        Map<Tuple, List<BlogPost>> postsPerTypeAndAuthor = posts.stream()
            .collect(groupingBy(post -> new Tuple(post.getType(), post.getAuthor())));

        List<BlogPost> result = postsPerTypeAndAuthor.get(new Tuple(BlogPostType.GUIDE, "Author 1"));

        assertThat(result.size()).isEqualTo(1);

        BlogPost blogPost = result.get(0);

        assertThat(blogPost.getTitle()).isEqualTo("Programming guide");
        assertThat(blogPost.getType()).isEqualTo(BlogPostType.GUIDE);
        assertThat(blogPost.getAuthor()).isEqualTo("Author 1");
    }

    @Test
    public void givenAListOfPosts_whenGroupedByRecord_thenGetAMapBetweenRecordAndList() {

        Map<BlogPost.AuthPostTypesLikes, List<BlogPost>> postsPerTypeAndAuthor = posts.stream()
            .collect(groupingBy(post -> new BlogPost.AuthPostTypesLikes(post.getAuthor(), post.getType(), post.getLikes())));

        List<BlogPost> result = postsPerTypeAndAuthor.get(new BlogPost.AuthPostTypesLikes("Author 1", BlogPostType.GUIDE, 20));

        assertThat(result.size()).isEqualTo(1);

        BlogPost blogPost = result.get(0);

        assertThat(blogPost.getTitle()).isEqualTo("Programming guide");
        assertThat(blogPost.getType()).isEqualTo(BlogPostType.GUIDE);
        assertThat(blogPost.getAuthor()).isEqualTo("Author 1");
        assertThat(blogPost.getLikes()).isEqualTo(20);
    }

    @Test
    public void givenListOfPosts_whenGroupedByAuthor_thenGetAMapUsingCollectingAndThen() {

        Map<String, BlogPost.PostcountTitlesLikesStats> postsPerAuthor = posts.stream()
            .collect(groupingBy(BlogPost::getAuthor, collectingAndThen(toList(), list -> {
                long count = list.stream()
                    .map(BlogPost::getTitle)
                    .collect(counting());
                String titles = list.stream()
                    .map(BlogPost::getTitle)
                    .collect(joining(" : "));
                IntSummaryStatistics summary = list.stream()
                    .collect(summarizingInt(BlogPost::getLikes));
                return new BlogPost.PostcountTitlesLikesStats(count, titles, summary);
            })));

        BlogPost.PostcountTitlesLikesStats result = postsPerAuthor.get("Author 1");
        assertThat(result.postCount()).isEqualTo(3L);
        assertThat(result.titles()).isEqualTo("News item 1 : Programming guide : Tech review 2");
        assertThat(result.likesStats().getMax()).isEqualTo(20);
        assertThat(result.likesStats().getMin()).isEqualTo(15);
        assertThat(result.likesStats().getAverage()).isEqualTo(16.666d, offset(0.001d));
    }

    @Test
    public void givenListOfPosts_whenGroupedByAuthor_thenGetAMapUsingToMap() {

        int maxValLikes = 17;
        Map<String, BlogPost.TitlesBoundedSumOfLikes> postsPerAuthor = posts.stream()
            .collect(toMap(BlogPost::getAuthor, post -> {
                int likes = (post.getLikes() > maxValLikes) ? maxValLikes : post.getLikes();
                return new BlogPost.TitlesBoundedSumOfLikes(post.getTitle(), likes);
            }, (u1, u2) -> {
                int likes = (u2.boundedSumOfLikes() > maxValLikes) ? maxValLikes : u2.boundedSumOfLikes();
                return new BlogPost.TitlesBoundedSumOfLikes(u1.titles()
                    .toUpperCase() + " : "
                    + u2.titles()
                        .toUpperCase(),
                    u1.boundedSumOfLikes() + likes);
            }));
        
        BlogPost.TitlesBoundedSumOfLikes result = postsPerAuthor.get("Author 1");
        assertThat(result.titles()).isEqualTo("NEWS ITEM 1 : PROGRAMMING GUIDE : TECH REVIEW 2");
        assertThat(result.boundedSumOfLikes()).isEqualTo(47);
    }
}
