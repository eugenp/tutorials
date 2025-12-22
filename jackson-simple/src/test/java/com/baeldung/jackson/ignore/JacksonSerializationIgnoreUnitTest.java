package com.baeldung.jackson.ignore;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.ser.BeanPropertyWriter;
import tools.jackson.databind.ser.FilterProvider;
import tools.jackson.databind.ser.PropertyFilter;
import tools.jackson.databind.ser.PropertyWriter;
import tools.jackson.core.exc.StreamReadException;
import tools.jackson.databind.ser.std.SimpleBeanPropertyFilter;
import tools.jackson.databind.ser.std.SimpleFilterProvider;

public class JacksonSerializationIgnoreUnitTest {

    // tests - single entity to json

    // ignore

    @Test
    public final void givenOnlyNonDefaultValuesAreSerializedAndDtoHasOnlyDefaultValues_whenSerializing_thenCorrect() throws StreamReadException, IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final String dtoAsString = mapper.writeValueAsString(new MyDtoIncludeNonDefault());

        assertThat(dtoAsString, not(containsString("intValue")));
        System.out.println(dtoAsString);
    }

    @Test
    public final void givenOnlyNonDefaultValuesAreSerializedAndDtoHasNonDefaultValue_whenSerializing_thenCorrect() throws StreamReadException, IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final MyDtoIncludeNonDefault dtoObject = new MyDtoIncludeNonDefault();
        dtoObject.setBooleanValue(true);

        final String dtoAsString = mapper.writeValueAsString(dtoObject);

        assertThat(dtoAsString, containsString("booleanValue"));
        System.out.println(dtoAsString);
    }

    @Test
    public final void givenFieldIsIgnoredByName_whenDtoIsSerialized_thenCorrect() throws StreamReadException, IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final MyDtoIgnoreFieldByName dtoObject = new MyDtoIgnoreFieldByName();
        dtoObject.setBooleanValue(true);

        final String dtoAsString = mapper.writeValueAsString(dtoObject);

        assertThat(dtoAsString, not(containsString("intValue")));
        assertThat(dtoAsString, containsString("booleanValue"));
        System.out.println(dtoAsString);
    }

    @Test
    public final void givenFieldIsIgnoredDirectly_whenDtoIsSerialized_thenCorrect() throws StreamReadException, IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final MyDtoIgnoreField dtoObject = new MyDtoIgnoreField();

        final String dtoAsString = mapper.writeValueAsString(dtoObject);

        assertThat(dtoAsString, not(containsString("intValue")));
        assertThat(dtoAsString, containsString("booleanValue"));
        System.out.println(dtoAsString);
    }

    // @Ignore("Jackson 2.7.1-1 seems to have changed the API for this case")
    @Test
    public final void givenFieldTypeIsIgnored_whenDtoIsSerialized_thenCorrect() throws StreamReadException, IOException {
        final ObjectMapper mapper = JsonMapper.builder()
            .addMixIn(String[].class, MyMixInForIgnoreType.class)
            .build();
        final MyDtoWithSpecialField dtoObject = new MyDtoWithSpecialField();
        dtoObject.setBooleanValue(true);

        final String dtoAsString = mapper.writeValueAsString(dtoObject);

        assertThat(dtoAsString, containsString("intValue"));
        assertThat(dtoAsString, containsString("booleanValue"));
        assertThat(dtoAsString, not(containsString("stringValue")));
        System.out.println(dtoAsString);
    }

    @Test
    public final void givenNullsIgnoredOnClass_whenWritingObjectWithNullField_thenIgnored() throws JacksonException {
        final ObjectMapper mapper = new ObjectMapper();
        final MyDtoIgnoreNull dtoObject = new MyDtoIgnoreNull();

        final String dtoAsString = mapper.writeValueAsString(dtoObject);

        assertThat(dtoAsString, containsString("intValue"));
        assertThat(dtoAsString, containsString("booleanValue"));
        assertThat(dtoAsString, not(containsString("stringValue")));
        System.out.println(dtoAsString);
    }

    @Test
    public final void givenNullsIgnoredGlobally_whenWritingObjectWithNullField_thenIgnored() throws JacksonException {
        final ObjectMapper mapper = JsonMapper.builder()
            .changeDefaultPropertyInclusion(incl -> incl.withValueInclusion(Include.NON_NULL))
            .build();
        final MyDto dtoObject = new MyDto();

        final String dtoAsString = mapper.writeValueAsString(dtoObject);

        assertThat(dtoAsString, containsString("intValue"));
        assertThat(dtoAsString, containsString("booleanValue"));
        assertThat(dtoAsString, not(containsString("stringValue")));
        System.out.println(dtoAsString);
    }

}
