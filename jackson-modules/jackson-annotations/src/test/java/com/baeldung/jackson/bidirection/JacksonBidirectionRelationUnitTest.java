package com.baeldung.jackson.bidirection;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Test;

import com.baeldung.jackson.bidirection.jsonview.Views;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonBidirectionRelationUnitTest {

    @Test(expected = JsonMappingException.class)
    public void givenBidirectionRelation_whenSerializing_thenException() throws JsonProcessingException {
        final User user = new User(1, "John");
        final Item item = new Item(2, "book", user);
        user.addItem(item);

        new ObjectMapper().writeValueAsString(item);
    }

    @Test
    public void givenBidirectionRelation_whenUsingJacksonReferenceAnnotation_thenCorrect() throws JsonProcessingException {
        final UserWithRef user = new UserWithRef(1, "John");
        final ItemWithRef item = new ItemWithRef(2, "book", user);
        user.addItem(item);

        final String result = new ObjectMapper().writeValueAsString(item);

        assertThat(result, containsString("book"));
        assertThat(result, containsString("John"));
        assertThat(result, not(containsString("userItems")));
    }

    @Test
    public void givenBidirectionRelation_whenUsingJsonIdentityInfo_thenCorrect() throws JsonProcessingException {
        final UserWithIdentity user = new UserWithIdentity(1, "John");
        final ItemWithIdentity item = new ItemWithIdentity(2, "book", user);
        user.addItem(item);

        final String result = new ObjectMapper().writeValueAsString(item);

        assertThat(result, containsString("book"));
        assertThat(result, containsString("John"));
        assertThat(result, containsString("userItems"));
    }

    @Test
    public void givenBidirectionRelation_whenUsingJsonIgnore_thenCorrect() throws JsonProcessingException {
        final UserWithIgnore user = new UserWithIgnore(1, "John");
        final ItemWithIgnore item = new ItemWithIgnore(2, "book", user);
        user.addItem(item);

        final String result = new ObjectMapper().writeValueAsString(item);

        assertThat(result, containsString("book"));
        assertThat(result, containsString("John"));
        assertThat(result, not(containsString("userItems")));
    }

    @Test
    public void givenBidirectionRelation_whenUsingCustomSerializer_thenCorrect() throws JsonProcessingException {
        final UserWithSerializer user = new UserWithSerializer(1, "John");
        final ItemWithSerializer item = new ItemWithSerializer(2, "book", user);
        user.addItem(item);

        final String result = new ObjectMapper().writeValueAsString(item);

        assertThat(result, containsString("book"));
        assertThat(result, containsString("John"));
        assertThat(result, containsString("userItems"));
    }

    @Test
    public void givenBidirectionRelation_whenDeserializingUsingIdentity_thenCorrect() throws JsonProcessingException, IOException {
        final String json = "{\"id\":2,\"itemName\":\"book\",\"owner\":{\"id\":1,\"name\":\"John\",\"userItems\":[2]}}";

        final ItemWithIdentity item = new ObjectMapper().readerFor(ItemWithIdentity.class)
            .readValue(json);

        assertEquals(2, item.id);
        assertEquals("book", item.itemName);
        assertEquals("John", item.owner.name);
    }

    @Test
    public void givenBidirectionRelation_whenUsingCustomDeserializer_thenCorrect() throws JsonProcessingException, IOException {
        final String json = "{\"id\":2,\"itemName\":\"book\",\"owner\":{\"id\":1,\"name\":\"John\",\"userItems\":[2]}}";

        final ItemWithSerializer item = new ObjectMapper().readerFor(ItemWithSerializer.class)
            .readValue(json);
        assertEquals(2, item.id);
        assertEquals("book", item.itemName);
        assertEquals("John", item.owner.name);
    }

    @Test
    public void givenBidirectionRelation_whenUsingPublicJsonView_thenCorrect() throws JsonProcessingException {
        final UserWithView user = new UserWithView(1, "John");
        final ItemWithView item = new ItemWithView(2, "book", user);
        user.addItem(item);

        final String result = new ObjectMapper().writerWithView(Views.Public.class)
            .writeValueAsString(item);

        assertThat(result, containsString("book"));
        assertThat(result, containsString("John"));
        assertThat(result, not(containsString("userItems")));
    }

    @Test(expected = JsonMappingException.class)
    public void givenBidirectionRelation_whenUsingInternalJsonView_thenException() throws JsonProcessingException {
        final UserWithView user = new UserWithView(1, "John");
        final ItemWithView item = new ItemWithView(2, "book", user);
        user.addItem(item);

        new ObjectMapper().writerWithView(Views.Internal.class)
            .writeValueAsString(item);
    }

}