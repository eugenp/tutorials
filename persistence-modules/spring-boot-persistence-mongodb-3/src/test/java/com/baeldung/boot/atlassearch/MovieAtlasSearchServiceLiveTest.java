package com.baeldung.boot.atlassearch;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.bson.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.boot.atlassearch.service.MovieAtlasSearchService;
import com.mongodb.client.model.search.SearchScore;

@DirtiesContext
@RunWith(SpringRunner.class)
@TestPropertySource("/embedded.properties")
@SpringBootTest(classes = MongoDbAtlasSearchApplication.class)
public class MovieAtlasSearchServiceLiveTest {

    @Autowired
    private MovieAtlasSearchService service;

    @Test
    public void givenScoreBoost_thenFirstItemContainsPlot() {
        final String plot = "space";

        Document movies = service.late90sMovies(0, 1, plot, SearchScore.boost(2));

        assertTrue(movies.getList("rows", Document.class)
            .iterator()
            .next()
            .getString("fullplot")
            .contains(plot));
    }

    @Test
    public void givenFacetOperator_thenCorrespondingBucketsReturned() {
        final String genre = "Sci-Fi";

        Document meta = service.genresThroughTheDecades(genre);

        Long lowerBound = meta
            .get("count", Document.class)
            .getLong("lowerBound");

        Document genresFacetFirstBucket = meta.get("facet", Document.class)
            .get("genresFacet", Document.class)
            .getList("buckets", Document.class)
            .iterator()
            .next();

        Document yearFacetFirstBucket = meta.get("facet", Document.class)
            .get("yearFacet", Document.class)
            .getList("buckets", Document.class)
            .iterator()
            .next();

        assertEquals(lowerBound, genresFacetFirstBucket.getLong("count"));
        assertEquals(genre, genresFacetFirstBucket.getString("_id"));
        assertNotNull(yearFacetFirstBucket);
    }
}
