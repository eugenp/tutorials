package com.baeldung.hexagonal.repository.impl;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.baeldung.hexagonal.domain.Movie;
import com.baeldung.hexagonal.domain.repository.MovieRepository;
import com.baeldung.hexagonal.domain.repository.MovieSearchRepository;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

/**
 * {@link MovieRepository} implementation that uses MongoDB for storage.
 */
public class MongoDbMovieRepository implements MovieRepository, MovieSearchRepository {
    private final MongodExecutable mongodExecutable;
    @SuppressWarnings("unused")
    private final MongodProcess mongod;
    private final DB db;
    private final Gson gson = new Gson();

    @SuppressWarnings("deprecation")
    public MongoDbMovieRepository() throws UnknownHostException, IOException {
        final MongodStarter starter = MongodStarter.getDefaultInstance();
        final IMongodConfig mongodConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
            .net(new Net("localhost", 12345, Network.localhostIsIPv6()))
            .build();

        mongodExecutable = starter.prepare(mongodConfig);
        mongod = mongodExecutable.start();
        @SuppressWarnings("resource")
        MongoClient mongo = new MongoClient("localhost", 12345);
        db = mongo.getDB("moviesDB");
        db.createCollection("moviesCol", new BasicDBObject());
    }

    @Override
    public Set<Movie> searchMoviesByName(final String movieName) {
        final Set<Movie> searchResult = new HashSet<>();
        final BasicDBObject regexQuery = new BasicDBObject();
        regexQuery.put("name", new BasicDBObject("$regex", movieName).append("$options", "i"));

        try (DBCursor cursor = db.getCollection("moviesCol")
            .find(regexQuery)) {
            while (cursor.hasNext()) {
                DBObject dbObject = cursor.next();
                searchResult.add(gson.fromJson(dbObject.toString(), Movie.class));
            }
        }
        return searchResult;
    }

    @Override
    public void save(final Movie movie) {
        final BasicDBObject bdo = BasicDBObject.parse(gson.toJson(movie));
        bdo.put("_id", movie.getId());

        db.getCollection("moviesCol")
            .save(bdo);
    }

    @Override
    public Movie findById(final UUID movieId) {
        final DBCollection moviesCol = db.getCollection("moviesCol");
        final DBObject dbObject = moviesCol.findOne(new BasicDBObject("_id", movieId));

        if (dbObject == null) {
            return null;
        }
        return gson.fromJson(dbObject.toString(), Movie.class);
    }

    public void shutDown() {
        if (mongodExecutable != null) {
            mongodExecutable.stop();
        }
    }

    @Override
    public Movie findMovieByName(final String movieName) {
        final DBObject dbObject = db.getCollection("moviesCol")
            .findOne(new BasicDBObject("name", movieName));

        if (dbObject == null) {
            return null;
        }
        return gson.fromJson(dbObject.toString(), Movie.class);
    }

    @Override
    public void delete(final UUID movieId) {
        db.getCollection("moviesCol")
            .remove(new BasicDBObject("_id", movieId));
    }
}
