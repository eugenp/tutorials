package com.baeldung.boot.atlassearch.service;

import static com.mongodb.client.model.Aggregates.facet;
import static com.mongodb.client.model.Aggregates.limit;
import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Aggregates.replaceWith;
import static com.mongodb.client.model.Aggregates.search;
import static com.mongodb.client.model.Aggregates.searchMeta;
import static com.mongodb.client.model.Aggregates.skip;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.computed;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.search.SearchOperator.numberRange;
import static com.mongodb.client.model.search.SearchOperator.text;
import static com.mongodb.client.model.search.SearchOptions.searchOptions;
import static com.mongodb.client.model.search.SearchPath.fieldPath;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Facet;
import com.mongodb.client.model.search.SearchCount;

@Service
public class AtlasSearchService {

    @Autowired
    private MongoTemplate mongo;

    public static void debug(List<Bson> json) {
        StringBuilder builder = new StringBuilder();
        final AtomicBoolean first = new AtomicBoolean(true);
        json.forEach(bson -> {
            builder.append((first.get() ? "" : ",") 
                + bson.toBsonDocument().toJson()
            );

            first.set(false);
        });

        System.out.println(builder.toString());
    }

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

        debug(json);

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

        debug(json);

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
    
    /**
     * https://www.mongodb.com/docs/atlas/atlas-search/tutorial/compound-query-custom-score/#std-label-compound-query-custom-score-tutorial
     * - The highlight option splits the text surrounding the hit.
     * - nota: para o boost multiple, pode-se especificar quantos "text elements" quiser, pode estar tanto no must quanto should
     * @return docs found
     * @since 21 de ago de 2023
     * @author ulisses
     */
    public List<Document> scoring(String type, Object weight) {
        // types: constant, boost|boost-multiple, function
        // @see SearchScore

        // define clauses
        List<Document> mustClauses =
            Arrays.asList(
                new Document(
                    "range", new Document("path", "year")
                    .append("gte", 2013)
                    .append("lte", 2015)));
        
        List<Document> shouldClauses =
            Arrays.asList(
                new Document("text",
                    new Document("query", "snow")
                        .append("path", "title")
                        .append("score", new Document(type, (weight instanceof String) ? Document.parse((String) weight) : new Document("value", weight)))));
        
        Document highlightOption = new Document("path", "title");
        // define query
        Document agg =
            new Document("$search",
                new Document("index", "idx-movies")
                .append("compound",
                    new Document("must", mustClauses).append("should", shouldClauses))
                .append("highlight", highlightOption));
          
        MongoDatabase database = mongo.getDb();
        MongoCollection<Document> collection = database.getCollection("movies");

        List<Document> results = new ArrayList<>();
        // run query and print results                
        List<Bson> json = Arrays.asList(agg,
          limit(10), 
          project(fields(
            excludeId(), 
            include("title", "year"), 
            computed("score", new Document("$meta", "searchScore")), 
                computed("highlights", new Document("$meta", "searchHighlights")))));

        debug(json);

        collection.aggregate(json)
            .into(results);

        results.forEach(doc -> System.out.println(doc.toJson()));
        return results;
    }

    /**
     * https://www.mongodb.com/docs/atlas/atlas-search/tutorial/facet-tutorial/
     * 
     * - requires a non dynamic index
     * - 
     * @param type
     * @param weight
     * @return faceted results.
     * @since 22 de ago de 2023
     * @author ulisses
     */
    public List<Document> faceting(String type, Object weight) {
        MongoDatabase database = mongo.getDb();
        MongoCollection<Document> collection = database.getCollection("movies");
        
        Date origin = null;
        try {
            origin = new SimpleDateFormat("yyyy-MM-dd").parse("1921-11-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Document agg = new Document("$searchMeta", 
            new Document("index", "faceted")
            .append("facet", 
                new Document("operator", 
                    new Document("near", 
                        new Document("path", "released")
                                .append("origin", origin)
                        .append("pivot", 7776000000L)))
            .append("facets", 
                                        new Document("genresFacet",
                    new Document("type", "string").append("path", "genres"))
                .append("yearFacet",
                    new Document("type", "number").append("path", "year")
                    .append("boundaries", Arrays.asList(1910, 1920, 1930, 1940))
         ))));

        List<Document> results = new ArrayList<>();
        // run query and print results
        List<Bson> json = Arrays.asList(agg);

        debug(json);

        collection.aggregate(json)
            .into(results);

        results.forEach(doc -> System.out.println(doc.toJson()));
        return results;
    }
}
