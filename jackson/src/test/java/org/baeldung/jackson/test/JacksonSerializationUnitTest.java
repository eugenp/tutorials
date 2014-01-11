package org.baeldung.jackson.test;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import org.baeldung.jackson.dtos.Item;
import org.baeldung.jackson.dtos.MyDto;
import org.baeldung.jackson.dtos.MyDtoFieldNameChanged;
import org.baeldung.jackson.dtos.MyDtoNoAccessors;
import org.baeldung.jackson.dtos.MyDtoNoAccessorsAndFieldVisibility;
import org.baeldung.jackson.dtos.User;
import org.baeldung.jackson.try1.ItemSerializer;
import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.collect.Lists;

public class JacksonSerializationUnitTest {

    // tests - single entity to json

    @Test
    public final void whenSerializing_thenCorrect() throws JsonParseException, IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final String dtoAsString = mapper.writeValueAsString(new MyDto());

        assertThat(dtoAsString, containsString("intValue"));
        assertThat(dtoAsString, containsString("stringValue"));
        assertThat(dtoAsString, containsString("booleanValue"));
    }

    @Test
    public final void givenNameOfFieldIsChangedViaAnnotationOnGetter_whenSerializing_thenCorrect() throws JsonParseException, IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final MyDtoFieldNameChanged dtoObject = new MyDtoFieldNameChanged();
        dtoObject.setStringValue("a");

        final String dtoAsString = mapper.writeValueAsString(dtoObject);

        assertThat(dtoAsString, not(containsString("stringValue")));
        assertThat(dtoAsString, containsString("strVal"));
        System.out.println(dtoAsString);
    }

    // tests - serialize via accessors/fields

    @Test(expected = JsonMappingException.class)
    public final void givenObjectHasNoAccessors_whenSerializing_thenException() throws JsonParseException, IOException {
        final String dtoAsString = new ObjectMapper().writeValueAsString(new MyDtoNoAccessors());

        assertThat(dtoAsString, notNullValue());
    }

    @Test
    public final void givenObjectHasNoAccessors_whenSerializingWithPrivateFieldsVisibility_thenNoException() throws JsonParseException, IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        final String dtoAsString = objectMapper.writeValueAsString(new MyDtoNoAccessors());

        assertThat(dtoAsString, containsString("intValue"));
        assertThat(dtoAsString, containsString("stringValue"));
        assertThat(dtoAsString, containsString("booleanValue"));
    }

    @Test
    public final void givenObjectHasNoAccessorsButHasVisibleFields_whenSerializing_thenNoException() throws JsonParseException, IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final String dtoAsString = objectMapper.writeValueAsString(new MyDtoNoAccessorsAndFieldVisibility());

        assertThat(dtoAsString, containsString("intValue"));
        assertThat(dtoAsString, containsString("stringValue"));
        assertThat(dtoAsString, containsString("booleanValue"));
    }

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
    public final void whenSerializingWithCustomSerializer_thenNoExceptions() throws JsonGenerationException, JsonMappingException, IOException {
        final Item myItem = new Item(Integer.parseInt(randomNumeric(2)), randomAlphabetic(8), new User(Integer.parseInt(randomNumeric(2)), randomAlphabetic(8)));

        final ObjectMapper mapper = new ObjectMapper();

        final SimpleModule simpleModule = new SimpleModule("SimpleModule", new Version(1, 0, 0, null));
        simpleModule.addSerializer(Item.class, new ItemSerializer());
        // simpleModule.addSerializer(User.class, new UserSerializer());
        mapper.registerModule(simpleModule);

        final StringWriter writer = new StringWriter();
        mapper.writeValue(writer, myItem);
        final String serialized = writer.toString();
        System.out.println(serialized);
    }

}
