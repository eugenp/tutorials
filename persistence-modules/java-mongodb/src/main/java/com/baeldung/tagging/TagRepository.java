package com.baeldung.tagging;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;

/**
 * Repository used to manage tags for a blog post.
 * 
 * @author Donato Rimenti
 *
 */
public class TagRepository implements Closeable {

	/**
	 * Document field which holds the blog tags.
	 */
	private static final String TAGS_FIELD = "tags";

	/**
	 * The post collection.
	 */
	private MongoCollection<Document> collection;

	/**
	 * The MongoDB client, stored in a field in order to close it later.
	 */
	private MongoClient mongoClient;

	/**
	 * Instantiates a new TagRepository by opening the DB connection.
	 */
	public TagRepository() {
		mongoClient = MongoClients.create("mongodb://localhost:27018");
		MongoDatabase database = mongoClient.getDatabase("blog");
		collection = database.getCollection("posts");
	}

	/**
	 * Returns a list of posts which contains one or more of the tags passed as
	 * argument.
	 * 
	 * @param tags
	 *            a list of tags
	 * @return a list of posts which contains at least one of the tags passed as
	 *         argument
	 */
	public List<Post> postsWithAtLeastOneTag(String... tags) {
		FindIterable<Document> results = collection.find(Filters.in(TAGS_FIELD, tags));
		return StreamSupport.stream(results.spliterator(), false).map(TagRepository::documentToPost)
				.collect(Collectors.toList());
	}

	/**
	 * Returns a list of posts which contains all the tags passed as argument.
	 * 
	 * @param tags
	 *            a list of tags
	 * @return a list of posts which contains all the tags passed as argument
	 */
	public List<Post> postsWithAllTags(String... tags) {
		FindIterable<Document> results = collection.find(Filters.all(TAGS_FIELD, tags));
		return StreamSupport.stream(results.spliterator(), false).map(TagRepository::documentToPost)
				.collect(Collectors.toList());
	}

	/**
	 * Returns a list of posts which contains none of the tags passed as
	 * argument.
	 * 
	 * @param tags
	 *            a list of tags
	 * @return a list of posts which contains none of the tags passed as
	 *         argument
	 */
	public List<Post> postsWithoutTags(String... tags) {
		FindIterable<Document> results = collection.find(Filters.nin(TAGS_FIELD, tags));
		return StreamSupport.stream(results.spliterator(), false).map(TagRepository::documentToPost)
				.collect(Collectors.toList());
	}

	/**
	 * Adds a list of tags to the blog post with the given title.
	 * 
	 * @param title
	 *            the title of the blog post
	 * @param tags
	 *            a list of tags to add
	 * @return the outcome of the operation
	 */
	public boolean addTags(String title, List<String> tags) {
		UpdateResult result = collection.updateOne(new BasicDBObject(DBCollection.ID_FIELD_NAME, title),
				Updates.addEachToSet(TAGS_FIELD, tags));
		return result.getModifiedCount() == 1;
	}

	/**
	 * Removes a list of tags to the blog post with the given title.
	 * 
	 * @param title
	 *            the title of the blog post
	 * @param tags
	 *            a list of tags to remove
	 * @return the outcome of the operation
	 */
	public boolean removeTags(String title, List<String> tags) {
		UpdateResult result = collection.updateOne(new BasicDBObject(DBCollection.ID_FIELD_NAME, title),
				Updates.pullAll(TAGS_FIELD, tags));
		return result.getModifiedCount() == 1;
	}

	/**
	 * Utility method used to map a MongoDB document into a {@link Post}.
	 * 
	 * @param document
	 *            the document to map
	 * @return a {@link Post} object equivalent to the document passed as
	 *         argument
	 */
	@SuppressWarnings("unchecked")
	private static Post documentToPost(Document document) {
		Post post = new Post();
		post.setTitle(document.getString(DBCollection.ID_FIELD_NAME));
		post.setAuthor(document.getString("author"));
		post.setTags((List<String>) document.get(TAGS_FIELD));
		return post;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() throws IOException {
		mongoClient.close();
	}

}
