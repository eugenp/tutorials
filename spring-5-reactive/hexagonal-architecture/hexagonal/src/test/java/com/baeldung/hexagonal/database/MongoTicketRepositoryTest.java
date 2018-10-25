package com.baeldung.hexagonal.database;

import com.baeldung.hexagonal.domain.SweepstakeNumbers;
import com.baeldung.hexagonal.domain.Sweepstake;
import com.baeldung.hexagonal.domain.SweepstakeId;
import com.baeldung.hexagonal.domain.PlayerDetails;
import com.baeldung.hexagonal.mongo.MongoConnectionPropertiesLoader;
import com.mongodb.MongoClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for Mongo based ticket repository
 */
@Disabled
class MongoTicketRepositoryTest {

  private static final String TEST_DB = "sweepstakesTestDB";
  private static final String TEST_TICKETS_COLLECTION = "sweepstakeTestTickets";
  private static final String TEST_COUNTERS_COLLECTION = "testCounters";

  private MongoTicketRepository repository;

  @BeforeEach
  void init() {
    MongoConnectionPropertiesLoader.load();
    MongoClient mongoClient = new MongoClient(System.getProperty("mongo-host"),
        Integer.parseInt(System.getProperty("mongo-port")));
    mongoClient.dropDatabase(TEST_DB);
    mongoClient.close();
    repository = new MongoTicketRepository(TEST_DB, TEST_TICKETS_COLLECTION,
        TEST_COUNTERS_COLLECTION);
  }

  @Test
  void testSetup() {
    assertEquals(1, repository.getCountersCollection().count());
    assertEquals(0, repository.getTicketsCollection().count());
  }

  @Test
  void testNextId() {
    assertEquals(1, repository.getNextId());
    assertEquals(2, repository.getNextId());
    assertEquals(3, repository.getNextId());
  }

  @Test
  void testCrudOperations() {
    
    PlayerDetails details = new PlayerDetails("foo@bar.com", "123-123", "07001234");
    SweepstakeNumbers random = SweepstakeNumbers.createRandom();
    Sweepstake original = new Sweepstake(new SweepstakeId(), details, random);
    Optional<SweepstakeId> saved = repository.save(original);
    assertEquals(1, repository.getTicketsCollection().count());
    assertTrue(saved.isPresent());
    Optional<Sweepstake> found = repository.findById(saved.get());
    assertTrue(found.isPresent());
    Sweepstake ticket = found.get();
    assertEquals("foo@bar.com", ticket.getPlayerDetails().getEmail());
    assertEquals("123-123", ticket.getPlayerDetails().getBankAccount());
    assertEquals("07001234", ticket.getPlayerDetails().getPhoneNumber());
    assertEquals(original.getNumbers(), ticket.getNumbers());
    // clear the collection
    repository.deleteAll();
    assertEquals(0, repository.getTicketsCollection().count());
  }
}
