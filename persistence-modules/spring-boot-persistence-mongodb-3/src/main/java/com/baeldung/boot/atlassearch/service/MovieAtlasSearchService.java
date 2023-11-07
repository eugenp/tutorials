package com.baeldung.boot.atlassearch.service;

import static com.mongodb.client.model.Aggregates.facet;
import static com.mongodb.client.model.Aggregates.limit;
import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Aggregates.replaceWith;
import static com.mongodb.client.model.Aggregates.search;
import static com.mongodb.client.model.Aggregates.searchMeta;
import static com.mongodb.client.model.Aggregates.skip;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Projections.metaSearchScore;
import static com.mongodb.client.model.search.SearchCollector.facet;
import static com.mongodb.client.model.search.SearchCount.total;
import static com.mongodb.client.model.search.SearchFacet.numberFacet;
import static com.mongodb.client.model.search.SearchFacet.stringFacet;
import static com.mongodb.client.model.search.SearchOperator.compound;
import static com.mongodb.client.model.search.SearchOperator.numberRange;
import static com.mongodb.client.model.search.SearchOperator.text;
import static com.mongodb.client.model.search.SearchOptions.searchOptions;
import static com.mongodb.client.model.search.SearchPath.fieldPath;
import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.logging.log4j.LogManager;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.baeldung.boot.atlassearch.config.IndexConfig;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Facet;
import com.mongodb.client.model.search.SearchScore;

@Service
public class MovieAtlasSearchService {

    @Autowired
    private IndexConfig config;

    private final MongoCollection<Document> collection;

    public MovieAtlasSearchService(MongoTemplate mongoTemplate) {
        MongoDatabase database = mongoTemplate.getDb();
        this.collection = database.getCollection("movies");
    }

    public static void debug(List<Bson> pipeline) {
        StringBuilder builder = new StringBuilder("[");
        final AtomicBoolean first = new AtomicBoolean(true);
        pipeline.forEach(stage -> {
            builder.append((first.get() ? "" : ",") 
                + stage.toBsonDocument()
            );

            first.set(false);
        });
        builder.append("]");

        LogManager.getLogger(MovieAtlasSearchService.class)
          .debug(builder.toString());
    }

    public Document late90sMovies(int skip, int limit, String keywords, SearchScore modifier) {
        List<Bson> pipeline = asList(
            search(
              compound()
                .must(asList(
                  numberRange(
                    fieldPath("year"))
                    .gteLt(1995, 2000)
                ))
                .should(asList(
                  text(
                    fieldPath("fullplot"), keywords
                  )
                  .score(modifier)
                )),
              searchOptions()
                .index(config.getQueryIndex())
            ),
            project(fields(
              excludeId(),
              include("title", "year", "fullplot", "imdb.rating"),
              metaSearchScore("score")
            )),
            facet(
              new Facet("rows", 
                skip(skip), 
                limit(limit)
              ),
              new Facet("totalRows",
                replaceWith("$$SEARCH_META"),
                limit(1)
              )
            )
        );

        debug(pipeline);
        return collection.aggregate(pipeline)
          .first();
    }
    
    public Document countLate90sMovies(String keywords) {
        List<Bson> pipeline = asList(
            searchMeta(
              compound()
                .must(asList(
                  numberRange(
                    fieldPath("year"))
                    .gteLt(1995, 2000),
                  text(
                    fieldPath("fullplot"), keywords
                  )
                )),
              searchOptions()
                .index(config.getQueryIndex())
                .count(total())
            )
        );

        debug(pipeline);
        return collection.aggregate(pipeline)
          .first();
    }
    
    public Collection<Document> moviesByKeywords(String keywords) {
        List<Bson> pipeline = asList(
            search(
              text(
                fieldPath("fullplot"), keywords
              ),
              searchOptions()
                .index(config.getQueryIndex())
            ),
            project(fields(
              excludeId(),
              include("title", "year", "fullplot", "imdb.rating")
            ))
        );

        debug(pipeline);
        return collection.aggregate(pipeline)
          .into(new ArrayList<>());
    }

    public Document genresThroughTheDecades(String genre) {
        List<Bson> pipeline = asList(
          searchMeta(
            facet(
              text(
                fieldPath("genres"), genre
              ),
              asList(
                stringFacet("genresFacet",
                  fieldPath("genres")
                ).numBuckets(5),
                numberFacet("yearFacet",
                  fieldPath("year"),
                  asList(1900, 1930, 1960, 1990, 2020)
                )
              )
            ),
            searchOptions()
              .index(config.getFacetIndex())
          )
        );

        debug(pipeline);
        return collection.aggregate(pipeline)
          .first();
    }
}
