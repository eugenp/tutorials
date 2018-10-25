package com.baeldung.hexagonal.eventlog;

import org.bson.Document;

import com.baeldung.hexagonal.domain.PlayerDetails;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoEventLog implements SweepstakeEventLog {
	private static final String DEFAULT_DB = "sweepstakeDB";
	  private static final String DEFAULT_EVENTS_COLLECTION = "events";

	  
	  private MongoClient mongoClient;
	  private MongoDatabase database;
	  private MongoCollection<Document> eventsCollection;

	  private StdOutEventLog stdOutEventLog = new StdOutEventLog();

	  /**
	   * Constructor
	   */
	  public MongoEventLog() {
	    connect();
	  }

	  /**
	   * Constructor accepting parameters
	   */
	  public MongoEventLog(String dbName, String eventsCollectionName) {
	    connect(dbName, eventsCollectionName);
	  }

	  /**
	   * Connect to database with default parameters
	   */
	  public void connect() {
	    connect(DEFAULT_DB, DEFAULT_EVENTS_COLLECTION);
	  }

	  /**
	   * Connect to database with given parameters
	   */
	  public void connect(String dbName, String eventsCollectionName) {
	    if (mongoClient != null) {
	      mongoClient.close();
	    }
	    mongoClient = new MongoClient(System.getProperty("mongo-host"),
	        Integer.parseInt(System.getProperty("mongo-port")));
	    database = mongoClient.getDatabase(dbName);
	    eventsCollection = database.getCollection(eventsCollectionName);
	  }

	  /**
	   * @return mongo client
	   */
	  public MongoClient getMongoClient() {
	    return mongoClient;
	  }

	  /**
	   *
	   * @return mongo database
	   */
	  public MongoDatabase getMongoDatabase() {
	    return database;
	  }

	  /**
	   *
	   * @return accounts collection
	   */
	  public MongoCollection<Document> getEventsCollection() {
	    return eventsCollection;
	  }


	  @Override
	  public void ticketSubmitted(PlayerDetails details) {
	    Document document = new Document("email", details.getEmail());
	    document.put("phone", details.getPhoneNumber());
	    document.put("bank", details.getBankAccount());
	    document.put("message", "Lottery ticket was submitted and bank account was charged for 3 credits.");
	    eventsCollection.insertOne(document);
	    stdOutEventLog.ticketSubmitted(details);
	  }

	  @Override
	  public void ticketSubmitError(PlayerDetails details) {
	    Document document = new Document("email", details.getEmail());
	    document.put("phone", details.getPhoneNumber());
	    document.put("bank", details.getBankAccount());
	    document.put("message", "Lottery ticket could not be submitted because lack of funds.");
	    eventsCollection.insertOne(document);
	    stdOutEventLog.ticketSubmitError(details);
	  }

	  @Override
	  public void ticketDidNotWin(PlayerDetails details) {
	    Document document = new Document("email", details.getEmail());
	    document.put("phone", details.getPhoneNumber());
	    document.put("bank", details.getBankAccount());
	    document.put("message", "Lottery ticket was checked and unfortunately did not win this time.");
	    eventsCollection.insertOne(document);
	    stdOutEventLog.ticketDidNotWin(details);
	  }

	  @Override
	  public void ticketWon(PlayerDetails details, int prizeAmount) {
	    Document document = new Document("email", details.getEmail());
	    document.put("phone", details.getPhoneNumber());
	    document.put("bank", details.getBankAccount());
	    document.put("message", String.format("Lottery ticket won! The bank account was deposited with %d credits.",
	        prizeAmount));
	    eventsCollection.insertOne(document);
	    stdOutEventLog.ticketWon(details, prizeAmount);
	  }

	  @Override
	  public void prizeError(PlayerDetails details, int prizeAmount) {
	    Document document = new Document("email", details.getEmail());
	    document.put("phone", details.getPhoneNumber());
	    document.put("bank", details.getBankAccount());
	    document.put("message", String.format("Lottery ticket won! Unfortunately the bank credit transfer of %d failed.",
	        prizeAmount));
	    eventsCollection.insertOne(document);
	    stdOutEventLog.prizeError(details, prizeAmount);
	  }
}
