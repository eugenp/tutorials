package com.baeldung.jackson.annotation.dtos;

import com.baeldung.jackson.annotation.dtos.withEnum.DistanceEnumWithValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.*;

public class ItemTest {

    @Test
    public void getItem() throws JsonProcessingException {
        User user = new User(1,"Bipin");
        Item item = new Item(1,"packet",user);
        final String test = new ObjectMapper().writeValueAsString(item);

    }
}