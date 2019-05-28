package com.baeldung.mapdb;

import org.junit.Test;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

import java.util.NavigableSet;

import static junit.framework.Assert.assertEquals;

public class TransactionsUnitTest {

  //in the article, split this into sections. -
  @Test
  public void givenValidDBSetup_whenTransactionCommittedAndRolledBack_checkPreviousStateAchieved() {

    //make sure we point this out, transactionEnable. Otherwsie an UnsupportedOperationException is thrown.
    DB db = DBMaker.memoryDB().transactionEnable().make();

    NavigableSet<String> set = db.
            treeSet("mySet")
            .serializer(Serializer.STRING)
            .createOrOpen();


    set.add("One");
    set.add("Two");

    db.commit();

    assertEquals(2, set.size());

    set.add("Three");

    assertEquals(3, set.size());

    db.rollback();

    assertEquals(2, set.size());

    db.close();
  }
}
