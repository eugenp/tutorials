package com.baeldung.mapdb;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

public class HTreeMapUnitTest {

    @Test
    public void givenValidDB_whenHTreeMapAddedToAndRetrieved_CheckedRetrievalCorrect() {

        DB db = DBMaker.memoryDB().make();

        HTreeMap<String, String> hTreeMap = db
          .hashMap("myTreMap")
          .keySerializer(Serializer.STRING)
          .valueSerializer(Serializer.STRING)
          .create();

        hTreeMap.put("key1", "value1");
        hTreeMap.put("key2", "value2");

        assertEquals(2, hTreeMap.size());

        //add another value with the same key

        hTreeMap.put("key1", "value3");

        assertEquals(2, hTreeMap.size());
        assertEquals("value3", hTreeMap.get("key1"));

    }

}
