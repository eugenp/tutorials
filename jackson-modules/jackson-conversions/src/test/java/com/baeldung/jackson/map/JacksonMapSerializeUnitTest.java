package com.baeldung.jackson.map;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.MapSerializer;

public class JacksonMapSerializeUnitTest {

    @JsonSerialize(keyUsing = MyPairSerializer.class)
    private Map<MyPair, String> map;

    @JsonSerialize(keyUsing = MapSerializer.class)
    private Map<MyPair, MyPair> cmap;

    @JsonSerialize(keyUsing = MyPairSerializer.class)
    private MyPair mapKey;

    @JsonSerialize(keyUsing = MyPairSerializer.class)
    private MyPair mapValue;

    final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void whenSimpleMapSerialize_thenCorrect() throws JsonProcessingException {

        Map<String, String> map = new HashMap<>();
        map.put("key", "value");

        final String jsonResult = mapper.writeValueAsString(map);

        Assert.assertEquals("{\"key\":\"value\"}", jsonResult);
    }

    @Test
    public void whenCustomObjectStringMapSerialize_thenCorrect() throws JsonProcessingException {

        map = new HashMap<>();
        MyPair key = new MyPair("Abbott", "Costello");
        map.put(key, "Comedy");

        final String jsonResult = mapper.writeValueAsString(map);

        Assert.assertEquals("{\"Abbott and Costello\":\"Comedy\"}", jsonResult);
    }

    @Test
    public void whenCustomObjectObjectMapSerialize_thenCorrect() throws JsonProcessingException {

        cmap = new HashMap<>();
        mapKey = new MyPair("Abbott", "Costello");
        mapValue = new MyPair("Comedy", "1940's");
        cmap.put(mapKey, mapValue);

        final String jsonResult = mapper.writeValueAsString(cmap);

        Assert.assertEquals("{\"Abbott and Costello\":\"Comedy and 1940's\"}", jsonResult);
    }
}
