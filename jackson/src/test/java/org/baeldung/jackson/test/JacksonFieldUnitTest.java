package org.baeldung.jackson.test;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.baeldung.jackson.field.MyDtoAccessLevel;
import org.baeldung.jackson.field.MyDtoCustomizedPropertyName;
import org.baeldung.jackson.field.MyDtoGetter;
import org.baeldung.jackson.field.MyDtoGetterImplicitDeserialization;
import org.baeldung.jackson.field.MyDtoSetter;
import org.baeldung.jackson.field.User;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonFieldUnitTest {

    @Test
    public final void givenDifferentAccessLevels_whenPrivateOrPackage_thenNotSerializable_whenPublic_thenSerializable() throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();

        final MyDtoAccessLevel dtoObject = new MyDtoAccessLevel();

        final String dtoAsString = mapper.writeValueAsString(dtoObject);
        assertThat(dtoAsString, not(containsString("stringValue")));
        assertThat(dtoAsString, not(containsString("intValue")));
        assertThat(dtoAsString, containsString("booleanValue"));
        System.out.println(dtoAsString);
    }

    @Test
    public final void givenDifferentAccessLevels_whenGetterAdded_thenSerializable() throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();

        final MyDtoGetter dtoObject = new MyDtoGetter();

        final String dtoAsString = mapper.writeValueAsString(dtoObject);
        assertThat(dtoAsString, containsString("stringValue"));
        assertThat(dtoAsString, not(containsString("intValue")));
        assertThat(dtoAsString, containsString("booleanValue"));
        System.out.println(dtoAsString);
    }

    @Test
    public final void givenDifferentAccessLevels_whenGetterAdded_thenDeserializable() throws JsonProcessingException, JsonMappingException, IOException {
        final String jsonAsString = "{\"stringValue\":\"dtoString\",\"intValue\":1,\"booleanValue\":\"true\"}";
        final ObjectMapper mapper = new ObjectMapper();

        final MyDtoGetterImplicitDeserialization dtoObject = mapper.readValue(jsonAsString, MyDtoGetterImplicitDeserialization.class);

        assertNotNull(dtoObject);
        assertThat(dtoObject.getStringValue(), equalTo("dtoString"));
        assertThat(dtoObject.getIntValue(), equalTo(1));
        assertThat(dtoObject.booleanValue, equalTo(true));
    }

    @Test
    public final void givenDifferentAccessLevels_whenSetterAdded_thenDeserializable() throws JsonProcessingException, JsonMappingException, IOException {
        final String jsonAsString = "{\"stringValue\":\"dtoString\",\"intValue\":1,\"booleanValue\":\"true\"}";
        final ObjectMapper mapper = new ObjectMapper();

        final MyDtoSetter dtoObject = mapper.readValue(jsonAsString, MyDtoSetter.class);

        assertNotNull(dtoObject);
        assertThat(dtoObject.getStringValue(), equalTo("dtoString"));
        assertThat(dtoObject.anotherGetIntValue(), equalTo(1));
        assertThat(dtoObject.booleanValue, equalTo(true));
    }

    @Test
    public final void givenDifferentAccessLevels_whenSetterAdded_thenStillNotSerializable() throws JsonProcessingException, JsonMappingException, IOException {
        final ObjectMapper mapper = new ObjectMapper();

        final MyDtoSetter dtoObject = new MyDtoSetter();

        final String dtoAsString = mapper.writeValueAsString(dtoObject);
        assertThat(dtoAsString, containsString("stringValue"));
        assertThat(dtoAsString, not(containsString("intValue")));
        assertThat(dtoAsString, containsString("booleanValue"));
        System.out.println(dtoAsString);
    }

    @Test
    public final void givenCustomizedPropertyName_whenFieldAnnotated_thenPropertyNameCustomizedOnSerialization() throws JsonProcessingException, JsonMappingException, IOException {
        final ObjectMapper mapper = new ObjectMapper();

        final MyDtoCustomizedPropertyName dtoObject = new MyDtoCustomizedPropertyName();

        final String dtoAsString = mapper.writeValueAsString(dtoObject);
        assertThat(dtoAsString, containsString("stringValue"));
        assertThat(dtoAsString, containsString("intValue"));
        assertThat(dtoAsString, containsString("BOOLEANVALUE"));
        assertThat(dtoAsString, not(containsString("booleanValue")));
        System.out.println(dtoAsString);
    }

    @Test
    public final void givenCustomizedPropertyName_whenFieldAnnotated_thenPropertyNameCustomizedOnDeserialization() throws JsonProcessingException, JsonMappingException, IOException {
        final String jsonAsString = "{\"stringValue\":\"dtoString\",\"intValue\":1,\"BOOLEANVALUE\":\"true\"}";
        final ObjectMapper mapper = new ObjectMapper();

        final MyDtoCustomizedPropertyName dtoObject = mapper.readValue(jsonAsString, MyDtoCustomizedPropertyName.class);

        assertNotNull(dtoObject);
        assertThat(dtoObject.getStringValue(), equalTo("dtoString"));
        assertThat(dtoObject.getIntValue(), equalTo(1));
        assertThat(dtoObject.getBooleanValue(), equalTo(true));
    }

    @Test
    public final void givenFieldIsIgnoredOnlyAtSerialization_whenUserIsSerialized_thenIgnored() throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();

        final User userObject = new User();
        userObject.setId(1);
        userObject.setName("theUser");
        userObject.setPassword("thePassword");

        final String userAsString = mapper.writeValueAsString(userObject);
        assertThat(userAsString, containsString("id"));
        assertThat(userAsString, containsString("name"));
        assertThat(userAsString, not(containsString("password")));
        System.out.println(userAsString);
    }

    @Test
    public final void givenFieldIsIgnoredOnlyAtSerialization_whenUserIsDeserialized_thenCorrect() throws JsonParseException, JsonMappingException, IOException {
        final String jsonAsString = "{\"id\":1,\"name\":\"theUser\",\"password\":\"thePassword\"}";
        final ObjectMapper mapper = new ObjectMapper();

        final User userObject = mapper.readValue(jsonAsString, User.class);

        assertNotNull(userObject);
        assertThat(userObject.getId(), equalTo(1));
        assertThat(userObject.getName(), equalTo("theUser"));
        assertThat(userObject.getPassword(), equalTo("thePassword"));
    }

}
