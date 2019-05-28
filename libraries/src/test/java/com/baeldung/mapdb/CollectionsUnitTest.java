package com.baeldung.mapdb;

import org.junit.Test;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

import java.util.NavigableSet;

import static junit.framework.Assert.assertEquals;

public class CollectionsUnitTest {

  @Test
  public void givenSetCreatedInDB_whenMultipleElementsAdded_checkOnlyOneExists() {

    DB db = DBMaker.memoryDB().make();

    NavigableSet<String> set = db.
            treeSet("mySet")
            .serializer(Serializer.STRING)
            .createOrOpen();

    final String MY_STRING = "Baeldung!";

    set.add(MY_STRING);
    set.add(MY_STRING);

    assertEquals(1, set.size());

    db.close();
  }
}
