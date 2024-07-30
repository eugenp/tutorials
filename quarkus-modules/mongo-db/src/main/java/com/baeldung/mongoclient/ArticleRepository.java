package com.baeldung.mongoclient;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ArticleRepository {

    @Inject
    MongoClient mongoClient;

    private MongoCollection<Article> getCollection() {
        return mongoClient.getDatabase("articles").getCollection("articles", Article.class);
    }

    public void create(Article article) {
        getCollection().insertOne(article);
    }

    public List<Article> listAll() {
        return getCollection().find().into(new ArrayList<>());
    }

    public void update(Article article) {
        getCollection().replaceOne(new org.bson.Document("_id", article.id), article);
    }

    public void delete(String id) {
        getCollection().deleteOne(new org.bson.Document("_id", new ObjectId(id)));
    }

    public void deleteAll() {
        getCollection().deleteMany(new Document());
    }
}