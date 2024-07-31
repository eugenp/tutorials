package com.baeldung.mapdb;

import static junit.framework.Assert.assertEquals;

import java.util.NavigableSet;

import org.junit.Test;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

public class CollectionsUnitTest {

    @Test
    public void givenSetCreatedInDB_whenMultipleElementsAdded_checkOnlyOneExists() {

        DB db = DBMaker.memoryDB().make();

        NavigableSet<String> set = db.
          treeSet("mySet")
          .serializer(Serializer.STRING)
          .createOrOpen();

        String myString = "Baeldung!";

        set.add(myString);
        set.add(myString);

        assertEquals(1, set.size());

        db.close();
    }
}
