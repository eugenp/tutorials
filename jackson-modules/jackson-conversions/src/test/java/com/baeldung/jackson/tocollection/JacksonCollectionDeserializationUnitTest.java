package com.baeldung.jackson.tocollection;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.google.common.collect.Lists;

public class JacksonCollectionDeserializationUnitTest {

    // tests - json to multiple entity

    @Test
    public final void givenJsonArray_whenDeserializingAsArray_thenCorrect() throws JsonParseException, JsonMappingException, IOException {
        final ObjectMapper mapper = new ObjectMapper();

        final List<MyDto> listOfDtos = Lists.newArrayList(new MyDto("a", 1, true), new MyDto("bc", 3, false));
        final String jsonArray = mapper.writeValueAsString(listOfDtos);
        // [{"stringValue":"a","intValue":1,"booleanValue":true},{"stringValue":"bc","intValue":3,"booleanValue":false}]

        final MyDto[] asArray = mapper.readValue(jsonArray, MyDto[].class);
        assertThat(asArray[0], instanceOf(MyDto.class));
    }

    @Test
    public final void givenJsonArray_whenDeserializingAsListWithNoTypeInfo_thenNotCorrect() throws JsonParseException, JsonMappingException, IOException {
        final ObjectMapper mapper = new ObjectMapper();

        final List<MyDto> listOfDtos = Lists.newArrayList(new MyDto("a", 1, true), new MyDto("bc", 3, false));
        final String jsonArray = mapper.writeValueAsString(listOfDtos);
        // [{"stringValue":"a","intValue":1,"booleanValue":true},{"stringValue":"bc","intValue":3,"booleanValue":false}]

        final List<MyDto> asList = mapper.readValue(jsonArray, List.class);
        assertThat((Object) asList.get(0), instanceOf(LinkedHashMap.class));
    }

    @Test
    public final void givenJsonArray_whenDeserializingAsListWithTypeReferenceHelp_thenCorrect() throws JsonParseException, JsonMappingException, IOException {
        final ObjectMapper mapper = new ObjectMapper();

        final List<MyDto> listOfDtos = Lists.newArrayList(new MyDto("a", 1, true), new MyDto("bc", 3, false));
        final String jsonArray = mapper.writeValueAsString(listOfDtos);
        // [{"stringValue":"a","intValue":1,"booleanValue":true},{"stringValue":"bc","intValue":3,"booleanValue":false}]

        final List<MyDto> asList = mapper.readValue(jsonArray, new TypeReference<List<MyDto>>() {
        });
        assertThat(asList.get(0), instanceOf(MyDto.class));
    }

    @Test
    public final void givenJsonArray_whenDeserializingAsListWithJavaTypeHelp_thenCorrect() throws JsonParseException, JsonMappingException, IOException {
        final ObjectMapper mapper = new ObjectMapper();

        final List<MyDto> listOfDtos = Lists.newArrayList(new MyDto("a", 1, true), new MyDto("bc", 3, false));
        final String jsonArray = mapper.writeValueAsString(listOfDtos);
        // [{"stringValue":"a","intValue":1,"booleanValue":true},{"stringValue":"bc","intValue":3,"booleanValue":false}]

        final CollectionType javaType = mapper.getTypeFactory()
            .constructCollectionType(List.class, MyDto.class);
        final List<MyDto> asList = mapper.readValue(jsonArray, javaType);
        assertThat(asList.get(0), instanceOf(MyDto.class));
    }

}
// a (private) no-args constructor is required (simulate without)
