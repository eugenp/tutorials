package com.baeldung.boot.atlassearch.service;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Aggregates.limit;
import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Aggregates.replaceWith;
import static com.mongodb.client.model.Aggregates.search;
import static com.mongodb.client.model.Aggregates.searchMeta;
import static com.mongodb.client.model.Aggregates.skip;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.search.SearchOperator.*;
import static com.mongodb.client.model.search.SearchOptions.searchOptions;
import static com.mongodb.client.model.search.SearchPath.fieldPath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Facet;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.search.SearchCount;

@Service
public class AtlasSearchService {

    @Autowired
    private MongoTemplate mongo;

    public List<Document> whatever() {
        Document agg = new Document("query", "baseball")
            .append("path", "plot")
        ;

        MongoDatabase database = mongo.getDb();
        MongoCollection<Document> collection = database.getCollection("movies");

        // - pipeline order matters
        List<Bson> json = Arrays.asList(
            eq("$search", 
                new Document("index", "idx-movies").append("text", agg)
            ),
            skip(1), limit(2), // cannot be specified if using facet
          project(fields(
              excludeId(), 
              include("title", "plot")
            ))
        // , // works as well:
        // eq("$facet",
        // new Document("rows", Arrays.asList(
        // new Document("$skip", 0),
        // new Document("$limit", 2)
        // ))
        // .append("totalRows", Arrays.asList(
        // new Document("$replaceWith", "$$SEARCH_META"),
        // new Document("$limit", 1) // otherwise we get it repeated for each item
        // ))
        // )
        );

        System.out.println("[");
        json.forEach(bson -> {
            System.out.println(bson.toBsonDocument()
                .toJson());
        });
        System.out.println("]");

        List<Document> results = new ArrayList<>();
        AggregateIterable<Document> aggregate = collection.aggregate(json);
        aggregate.into(results);
        return results;
    }

    public List<Document> paginelson(int skip, int limit) {
        MongoDatabase database = mongo.getDb();
        MongoCollection<Document> collection = database.getCollection("movies");

        List<Bson> json = Arrays.asList(
            search(
                // available from 4.7+ https://www.mongodb.com/developer/products/atlas/atlas-search-java/
                text(fieldPath("plot"), "baseball"),
                searchOptions()
                    .index("idx-movies")
            ),
            // skip(skip), limit(limit), if using facet, cannot be specified here
            project(fields(
                excludeId(), 
                include("title", "plot")
                )),
            facet(
                new Facet("rows", skip(skip), limit(limit)),
                new Facet("totalRows", 
                    replaceWith("$$SEARCH_META"), 
                    limit(1) // otherwise we get it repeated for each item in the coll
                )
            )
        );

        List<Document> results = new ArrayList<>();
        AggregateIterable<Document> aggregate = collection.aggregate(json);
        aggregate.into(results);
        return results;
    }
    
    public List<Document> meta(String grouping) {
        MongoDatabase database = mongo.getDb();
        MongoCollection<Document> collection = database.getCollection("movies");

        List<Bson> json = Arrays.asList(
            searchMeta(
                numberRange(fieldPath("year"))
                    .gteLt(1998, 1999),
                searchOptions()
                    .index("idx-movies")
                    .count(SearchCount.total())
            )
        );

        System.out.println("[");
        json.forEach(bson -> {
            System.out.println(bson.toBsonDocument()
                .toJson());
        });
        System.out.println("]");

        List<Document> results = new ArrayList<>();
        AggregateIterable<Document> aggregate = collection.aggregate(json);
        aggregate.into(results);
        return results;
    }
    
    public List<Document> grouping(String grouping) {
        MongoDatabase database = mongo.getDb();
        MongoCollection<Document> collection = database.getCollection("movies");

        List<Bson> json = Arrays.asList(
            search(
                // available from 4.7+ https://www.mongodb.com/developer/products/atlas/atlas-search-java/
                text(fieldPath("plot"), "baseball"),
                searchOptions()
                    .index("idx-movies") // zero results if wrong index name. if named "default", no need to specify
            ),
            // skip(skip), limit(limit), if using facet, cannot be specified here
            project(fields(
                excludeId(), 
                include("title", "plot")
                )),
            facet(
                // new Facet("rows", skip(skip), limit(limit)),
                new Facet("totalRows", 
                    replaceWith("$$SEARCH_META"), 
                    limit(1) // otherwise we get it repeated for each item in the coll
                )
            )
        );

        List<Document> results = new ArrayList<>();
        AggregateIterable<Document> aggregate = collection.aggregate(json);
        aggregate.into(results);
        return results;
    }
}
