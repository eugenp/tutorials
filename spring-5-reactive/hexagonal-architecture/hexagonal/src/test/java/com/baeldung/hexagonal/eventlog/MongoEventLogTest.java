package com.baeldung.hexagonal.eventlog;

import com.baeldung.hexagonal.domain.PlayerDetails;
import com.baeldung.hexagonal.mongo.MongoConnectionPropertiesLoader;
import com.mongodb.MongoClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for Mongo event log
 */
@Disabled
class MongoEventLogTest {

  private static final String TEST_DB = "sweepstakeDBTest";
  private static final String TEST_EVENTS_COLLECTION = "testEvents";

  private MongoEventLog mongoEventLog;

  @BeforeEach
  void init() {
    MongoConnectionPropertiesLoader.load();
    MongoClient mongoClient = new MongoClient(System.getProperty("mongo-host"),
        Integer.parseInt(System.getProperty("mongo-port")));
    mongoClient.dropDatabase(TEST_DB);
    mongoClient.close();
    mongoEventLog = new MongoEventLog(TEST_DB, TEST_EVENTS_COLLECTION);
  }

  @Test
  void testSetup() {
    assertEquals(0, mongoEventLog.getEventsCollection().count());
  }

  @Test
  void testFundTransfers() {
    PlayerDetails playerDetails = new PlayerDetails("alag@test.com", "000-000", "03432534543");
    mongoEventLog.prizeError(playerDetails, 1000);
    assertEquals(1, mongoEventLog.getEventsCollection().count());
    mongoEventLog.prizeError(playerDetails, 1000);
    assertEquals(2, mongoEventLog.getEventsCollection().count());
    mongoEventLog.ticketDidNotWin(playerDetails);
    assertEquals(3, mongoEventLog.getEventsCollection().count());
    mongoEventLog.ticketDidNotWin(playerDetails);
    assertEquals(4, mongoEventLog.getEventsCollection().count());
    mongoEventLog.ticketSubmitError(playerDetails);
    assertEquals(5, mongoEventLog.getEventsCollection().count());
    mongoEventLog.ticketSubmitError(playerDetails);
    assertEquals(6, mongoEventLog.getEventsCollection().count());
    mongoEventLog.ticketSubmitted(playerDetails);
    assertEquals(7, mongoEventLog.getEventsCollection().count());
    mongoEventLog.ticketSubmitted(playerDetails);
    assertEquals(8, mongoEventLog.getEventsCollection().count());
    mongoEventLog.ticketWon(playerDetails, 1000);
    assertEquals(9, mongoEventLog.getEventsCollection().count());
    mongoEventLog.ticketWon(playerDetails, 1000);
    assertEquals(10, mongoEventLog.getEventsCollection().count());
  }
}
