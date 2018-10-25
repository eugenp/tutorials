package com.baeldung.hexagonal.database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.bson.Document;

import com.baeldung.hexagonal.domain.PlayerDetails;
import com.baeldung.hexagonal.domain.Sweepstake;
import com.baeldung.hexagonal.domain.SweepstakeId;
import com.baeldung.hexagonal.domain.SweepstakeNumbers;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoTicketRepository implements SweepstakesRepository {
	private static final String DEFAULT_DB = "sweepstakeDB";
	  private static final String DEFAULT_TICKETS_COLLECTION = "sweepstakeEntries";
	  private static final String DEFAULT_COUNTERS_COLLECTION = "counters";

	  private MongoClient mongoClient;
	  private MongoDatabase database;
	  private MongoCollection<Document> ticketsCollection;
	  private MongoCollection<Document> countersCollection;

	  /**
	   * Constructor
	   */
	  public MongoTicketRepository() {
	    connect();
	  }

	  /**
	   * Constructor accepting parameters
	   */
	  public MongoTicketRepository(String dbName, String ticketsCollectionName,
	                               String countersCollectionName) {
	    connect(dbName, ticketsCollectionName, countersCollectionName);
	  }

	  /**
	   * Connect to database with default parameters
	   */
	  public void connect() {
	    connect(DEFAULT_DB, DEFAULT_TICKETS_COLLECTION, DEFAULT_COUNTERS_COLLECTION);
	  }

	  /**
	   * Connect to database with given parameters
	   */
	  public void connect(String dbName, String ticketsCollectionName,
	                      String countersCollectionName) {
	    if (mongoClient != null) {
	      mongoClient.close();
	    }
	    mongoClient = new MongoClient(System.getProperty("mongo-host"),
	        Integer.parseInt(System.getProperty("mongo-port")));
	    database = mongoClient.getDatabase(dbName);
	    ticketsCollection = database.getCollection(ticketsCollectionName);
	    countersCollection = database.getCollection(countersCollectionName);
	    if (countersCollection.count() <= 0) {
	      initCounters();
	    }
	  }

	  private void initCounters() {
	    Document doc = new Document("_id", "ticketId").append("seq", 1);
	    countersCollection.insertOne(doc);
	  }

	  /**
	   * @return next ticket id
	   */
	  public int getNextId() {
	    Document find = new Document("_id", "ticketId");
	    Document increase = new Document("seq", 1);
	    Document update = new Document("$inc", increase);
	    Document result = countersCollection.findOneAndUpdate(find, update);
	    return result.getInteger("seq");
	  }

	  /**
	   *
	   * @return tickets collection
	   */
	  public MongoCollection<Document> getTicketsCollection() {
	    return ticketsCollection;
	  }

	  /**
	   *
	   * @return counters collection
	   */
	  public MongoCollection<Document> getCountersCollection() {
	    return countersCollection;
	  }

	  @Override
	  public Optional<Sweepstake> findById(SweepstakeId id) {
	    Document find = new Document("ticketId", id.getId());
	    List<Document> results = ticketsCollection.find(find).limit(1).into(new ArrayList<Document>());
	    if (results.size() > 0) {
	    	Sweepstake lotteryTicket = docToTicket(results.get(0));
	      return Optional.of(lotteryTicket);
	    } else {
	      return Optional.empty();
	    }
	  }

	  @Override
	  public Optional<SweepstakeId> save(Sweepstake ticket) {
	    int ticketId = getNextId();
	    Document doc = new Document("ticketId", ticketId);
	    doc.put("email", ticket.getPlayerDetails().getEmail());
	    doc.put("bank", ticket.getPlayerDetails().getBankAccount());
	    doc.put("phone", ticket.getPlayerDetails().getPhoneNumber());
	    doc.put("numbers", ticket.getNumbers().getNumbersAsString());
	    ticketsCollection.insertOne(doc);
	    return Optional.of(new SweepstakeId(ticketId));
	  }

	  @Override
	  public Map<SweepstakeId, Sweepstake> findAll() {
	    Map<SweepstakeId, Sweepstake> map = new HashMap<>();
	    List<Document> docs = ticketsCollection.find(new Document()).into(new ArrayList<Document>());
	    for (Document doc: docs) {
	    	Sweepstake sweepstake = docToTicket(doc);
	      map.put(sweepstake.getId(), sweepstake);
	    }
	    return map;
	  }

	  @Override
	  public void deleteAll() {
	    ticketsCollection.deleteMany(new Document());
	  }

	  private Sweepstake docToTicket(Document doc) {
	    PlayerDetails playerDetails = new PlayerDetails(doc.getString("email"), doc.getString("bank"),
	        doc.getString("phone"));
	    int[] numArray = Arrays.asList(doc.getString("numbers").split(",")).stream().mapToInt(Integer::parseInt).toArray();
	    Set<Integer> numbers = new HashSet<>();
	    for (int num: numArray) {
	      numbers.add(num);
	    }
	    SweepstakeNumbers sweepstakeNumbers = SweepstakeNumbers.create(numbers);
	    return new Sweepstake(new SweepstakeId(doc.getInteger("ticketId")), playerDetails, sweepstakeNumbers);
	  }
}
