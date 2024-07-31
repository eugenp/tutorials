package com.baeldung.jackson.mapnull;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class JacksonMapNullUnitTest {

    @Test
    public final void givenIgnoringMapNullValue_whenWritingMapObjectWithNullValue_thenIgnored() throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        // mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        mapper.setSerializationInclusion(Include.NON_NULL);

        final MyDto dtoObject1 = new MyDto();

        final Map<String, MyDto> dtoMap = new HashMap<String, MyDto>();
        dtoMap.put("dtoObject1", dtoObject1);
        dtoMap.put("dtoObject2", null);

        final String dtoMapAsString = mapper.writeValueAsString(dtoMap);

        assertThat(dtoMapAsString, containsString("dtoObject1"));
        assertThat(dtoMapAsString, not(containsString("dtoObject2")));
        System.out.println(dtoMapAsString);
    }

    @Test
    public final void givenIgnoringMapValueObjectWithNullField_whenWritingMapValueObjectWithNullField_thenIgnored() throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);

        final MyDto dtoObject = new MyDto();

        final Map<String, MyDto> dtoMap = new HashMap<String, MyDto>();
        dtoMap.put("dtoObject", dtoObject);

        final String dtoMapAsString = mapper.writeValueAsString(dtoMap);

        assertThat(dtoMapAsString, containsString("dtoObject"));
        assertThat(dtoMapAsString, not(containsString("stringValue")));
        System.out.println(dtoMapAsString);
    }

    @Test
    public final void givenAllowingMapObjectWithNullKey_whenWriting_thenCorrect() throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.getSerializerProvider()
            .setNullKeySerializer(new MyDtoNullKeySerializer());

        final MyDto dtoObject1 = new MyDto();
        dtoObject1.setStringValue("dtoObjectString1");
        final MyDto dtoObject2 = new MyDto();
        dtoObject2.setStringValue("dtoObjectString2");

        final Map<String, MyDto> dtoMap = new HashMap<String, MyDto>();
        dtoMap.put(null, dtoObject1);
        dtoMap.put("obj2", dtoObject2);

        final String dtoMapAsString = mapper.writeValueAsString(dtoMap);

        System.out.println(dtoMapAsString);
        assertThat(dtoMapAsString, containsString("\"\""));
        assertThat(dtoMapAsString, containsString("dtoObjectString1"));
        assertThat(dtoMapAsString, containsString("obj2"));
    }

    @Test
    public final void givenAllowingMapObjectOneNullKey_whenWritingMapObjectWithTwoNullKeys_thenOverride() throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.getSerializerProvider()
            .setNullKeySerializer(new MyDtoNullKeySerializer());

        final MyDto dtoObject1 = new MyDto();
        dtoObject1.setStringValue("dtoObject1String");

        final MyDto dtoObject2 = new MyDto();
        dtoObject2.setStringValue("dtoObject2String");

        final Map<String, MyDto> dtoMap = new HashMap<String, MyDto>();
        dtoMap.put(null, dtoObject1);
        dtoMap.put(null, dtoObject2);

        final String dtoMapAsString = mapper.writeValueAsString(dtoMap);

        assertThat(dtoMapAsString, not(containsString("dtoObject1String")));
        assertThat(dtoMapAsString, containsString("dtoObject2String"));
        System.out.println(dtoMapAsString);
    }

}
