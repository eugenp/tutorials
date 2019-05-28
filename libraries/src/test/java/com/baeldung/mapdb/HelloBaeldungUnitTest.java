package com.baeldung.mapdb;

import org.junit.Test;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;

import java.util.concurrent.ConcurrentMap;

import static junit.framework.Assert.assertEquals;

public class HelloBaeldungUnitTest {

  @Test
  public void givenInMemoryDBInstantiateCorrectly_whenDataSavedAndRetrieved_checkRetrievalCorrect() {

    DB db = DBMaker.memoryDB().make();

    final String WELCOME_MESSAGE_KEY = "Welcome Message";
    final String WELCOME_MESSAGE = "Hello Baeldung!";

    HTreeMap myMap = db.hashMap("myMap").createOrOpen();
    myMap.put(WELCOME_MESSAGE_KEY, WELCOME_MESSAGE);

    String welcomeMessage = (String) myMap.get(WELCOME_MESSAGE_KEY);

    db.close();

    assertEquals(WELCOME_MESSAGE, welcomeMessage);

  }

  @Test
  public void givenInFileDBInstantiateCorrectly_whenDataSavedAndRetrieved_checkRetrievalCorrect() {

    DB db = DBMaker.fileDB("file.db").make();

    final String WELCOME_MESSAGE_KEY = "Welcome Message";
    final String WELCOME_MESSAGE = "Hello Baeldung!";

    HTreeMap myMap = db.hashMap("myMap").createOrOpen();
    myMap.put(WELCOME_MESSAGE_KEY, WELCOME_MESSAGE);

    String welcomeMessage = (String) myMap.get(WELCOME_MESSAGE_KEY);

    db.close();

    assertEquals(WELCOME_MESSAGE, welcomeMessage);

  }
}
