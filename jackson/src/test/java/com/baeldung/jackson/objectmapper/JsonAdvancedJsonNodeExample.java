package com.baeldung.jackson.objectmapper;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.jackson.objectmapper.dto.Car;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonAdvancedJsonNodeExample extends Example
{

    protected final Logger Logger = LoggerFactory.getLogger(getClass());

    public JsonAdvancedJsonNodeExample() { }

    String LOCAL_JSON = "{ \"color\" : \"Black\", \"type\" : \"Fiat\", \"year\" : \"1970\" }";

    @Override
    public String name()
    {
        return this.getClass().getName();
    }

    @Override
    public void testExample() throws Exception {

        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        final Car car = objectMapper.readValue(LOCAL_JSON, Car.class);
        final JsonNode jsonNodeRoot = objectMapper.readTree(LOCAL_JSON);
        final JsonNode jsonNodeYear = jsonNodeRoot.get("year");
        final String year = jsonNodeYear.asText();

        assertNotNull(car);
        assertThat(car.getColor(), equalTo("Black"));
        assertThat(year, containsString("1970"));
    }
}
