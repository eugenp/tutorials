package com.baeldung.mapdb;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.mapdb.*;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;

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
