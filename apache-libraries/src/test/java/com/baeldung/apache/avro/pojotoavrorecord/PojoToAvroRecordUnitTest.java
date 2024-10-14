package com.baeldung.apache.avro.pojotoavrorecord;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Map;

import org.apache.avro.generic.GenericData;
import org.apache.avro.util.Utf8;
import org.junit.Test;

public class PojoToAvroRecordUnitTest {

    Pojo pojo = new Pojo();

    @Test
    public void givenSFA_whenPojoIsConverted_ThenMappingIsPerformed(){

        GenericData.Record avroRecord = ConvertingPojoToAvroRecord.mapPojoToRecordStraightForward(pojo);

        assertEquals(pojo.getUid(), avroRecord.get("uid"));
        assertEquals(pojo.getLocalDateTime(), avroRecord.get("localDateTime"));
        assertEquals(pojo.getaMap().get("mapKey"), ((Map<String, String>)avroRecord.get("aMap")).get("mapKey"));
    }

    @Test
    public void givenReflectionApproach_whenPojoIsConverted_thenMappingIsPerformed() throws IllegalAccessException {

        GenericData.Record avroRecord = ConvertingPojoToAvroRecord.mapPojoToRecordReflection(pojo);

        assertEquals(pojo.getUid(), avroRecord.get("uid"));
        assertEquals(pojo.getLocalDateTime(), avroRecord.get("localDateTime"));
        assertEquals(pojo.getaMap().get("mapKey"), ((Map<String, String>)avroRecord.get("aMap")).get("mapKey"));
    }

    @Test
    public void givenReflectDatumWriter_whenPojoIsConverted_thenMappingIsPerformed() throws IOException {

        GenericData.Record avroRecord = ConvertingPojoToAvroRecord.mapPojoToRecordReflectDatumWriter(pojo);

        assertEquals(pojo.getUid(), avroRecord.get("uid"));
        assertEquals(pojo.getLocalDateTime(), avroRecord.get("localDateTime"));
        assertEquals(pojo.getaMap().get("mapKey"), ((Map<Utf8, Utf8>)avroRecord.get("aMap")).get(new Utf8("mapKey")).toString());
    }
}
