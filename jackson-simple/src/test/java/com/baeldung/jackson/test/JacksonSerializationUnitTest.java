package com.baeldung.jackson.test;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.List;

import com.baeldung.jackson.dtos.User;
import com.baeldung.jackson.dtos.Item;
import com.baeldung.jackson.dtos.ItemWithSerializer;
import com.baeldung.jackson.dtos.MyDto;
import com.baeldung.jackson.serialization.ItemSerializer;
import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.collect.Lists;

public class JacksonSerializationUnitTest {

    // tests - multiple entities to json

    @Test
    public final void whenDtoIsSerialized_thenCorrect() throws JsonParseException, IOException {
        final List<MyDto> listOfDtos = Lists.newArrayList(new MyDto("a", 1, true), new MyDto("bc", 3, false));

        final ObjectMapper mapper = new ObjectMapper();
        final String dtosAsString = mapper.writeValueAsString(listOfDtos);

        System.out.println(dtosAsString);
    }

    // tests - custom serializer

    @Test
    public final void whenSerializing_thenNoExceptions() throws JsonGenerationException, JsonMappingException, IOException {
        final Item myItem = new Item(1, "theItem", new User(2, "theUser"));
        final String serialized = new ObjectMapper().writeValueAsString(myItem);
        System.out.println(serialized);
    }

    @Test
    public final void whenSerializingWithCustomSerializer_thenNoExceptions() throws JsonGenerationException, JsonMappingException, IOException {
        final Item myItem = new Item(1, "theItem", new User(2, "theUser"));

        final ObjectMapper mapper = new ObjectMapper();

        final SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Item.class, new ItemSerializer());
        mapper.registerModule(simpleModule);

        final String serialized = mapper.writeValueAsString(myItem);
        System.out.println(serialized);
    }

    @Test
    public final void givenSerializerRegisteredOnClass_whenSerializingWithCustomSerializer_thenNoExceptions() throws JsonGenerationException, JsonMappingException, IOException {
        final ItemWithSerializer myItem = new ItemWithSerializer(1, "theItem", new User(2, "theUser"));

        final String serialized = new ObjectMapper().writeValueAsString(myItem);
        System.out.println(serialized);
    }

}
