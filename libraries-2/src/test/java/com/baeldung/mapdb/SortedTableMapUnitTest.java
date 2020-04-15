package com.baeldung.mapdb;

import org.junit.Test;
import org.mapdb.Serializer;
import org.mapdb.SortedTableMap;
import org.mapdb.volume.MappedFileVol;
import org.mapdb.volume.Volume;

import static junit.framework.Assert.assertEquals;

public class SortedTableMapUnitTest {

    private static final String VOLUME_LOCATION = "sortedTableMapVol.db";

    @Test
    public void givenValidSortedTableMapSetup_whenQueried_checkValuesCorrect() {

        //create memory mapped volume, readonly false
        Volume vol = MappedFileVol.FACTORY.makeVolume(VOLUME_LOCATION, false);

        //create sink to feed the map with data
        SortedTableMap.Sink<Integer, String> sink =
          SortedTableMap.create(
            vol,
            Serializer.INTEGER,
            Serializer.STRING
          ).createFromSink();

        //add content
        for(int i = 0; i < 100; i++){
          sink.put(i, "Value " + Integer.toString(i));
        }

        sink.create();

        //now open in read-only mode
        Volume openVol = MappedFileVol.FACTORY.makeVolume(VOLUME_LOCATION, true);

        SortedTableMap<Integer, String> sortedTableMap = SortedTableMap.open(
          openVol,
          Serializer.INTEGER,
          Serializer.STRING
        );

        assertEquals(100, sortedTableMap.size());
    }
}
