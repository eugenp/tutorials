package com.baeldung.mapdb;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;

public class HelloBaeldungUnitTest {

    @Test
    public void givenInMemoryDBInstantiateCorrectly_whenDataSavedAndRetrieved_checkRetrievalCorrect() {

        DB db = DBMaker.memoryDB()
            .make();

        String welcomeMessageKey = "Welcome Message";
        String welcomeMessageString = "Hello Baeldung!";

        HTreeMap myMap = db.hashMap("myMap")
            .createOrOpen();
        myMap.put(welcomeMessageKey, welcomeMessageString);

        String welcomeMessageFromDB = (String) myMap.get(welcomeMessageKey);

        db.close();

        assertEquals(welcomeMessageString, welcomeMessageFromDB);
    }

    @Test
    public void givenInFileDBInstantiateCorrectly_whenDataSavedAndRetrieved_checkRetrievalCorrect() {

        DB db = DBMaker.fileDB("file.db")
            .make();

        String welcomeMessageKey = "Welcome Message";
        String welcomeMessageString = "Hello Baeldung!";

        HTreeMap myMap = db.hashMap("myMap")
            .createOrOpen();
        myMap.put(welcomeMessageKey, welcomeMessageString);

        String welcomeMessageFromDB = (String) myMap.get(welcomeMessageKey);

        db.close();

        assertEquals(welcomeMessageString, welcomeMessageFromDB);
    }
}
