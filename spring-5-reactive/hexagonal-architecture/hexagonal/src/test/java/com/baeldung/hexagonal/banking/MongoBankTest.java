package com.baeldung.hexagonal.banking;

import com.baeldung.hexagonal.mongo.MongoConnectionPropertiesLoader;
import com.mongodb.MongoClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for Mongo banking adapter
 */
@Disabled
class MongoBankTest {

  private static final String TEST_DB = "sweepstakesDBTest";
  private static final String TEST_ACCOUNTS_COLLECTION = "testAccounts";

  private MongoBank mongoBank;

  @BeforeEach
  void init() {
    MongoConnectionPropertiesLoader.load();
    MongoClient mongoClient = new MongoClient(System.getProperty("mongo-host"),
        Integer.parseInt(System.getProperty("mongo-port")));
    mongoClient.dropDatabase(TEST_DB);
    mongoClient.close();
    mongoBank = new MongoBank(TEST_DB, TEST_ACCOUNTS_COLLECTION);
  }

  @Test
  void testSetup() {
    assertEquals(0, mongoBank.getAccountsCollection().count());
  }

  @Test
  void testFundTransfers() {
    assertEquals(0, mongoBank.getFunds("000-000"));
    mongoBank.setFunds("000-000", 10);
    assertEquals(10, mongoBank.getFunds("000-000"));
    assertEquals(0, mongoBank.getFunds("111-111"));
    mongoBank.transferFunds(9, "000-000", "111-111");
    assertEquals(1, mongoBank.getFunds("000-000"));
    assertEquals(9, mongoBank.getFunds("111-111"));
  }
}
