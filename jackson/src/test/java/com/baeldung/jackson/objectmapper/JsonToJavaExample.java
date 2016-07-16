package com.baeldung.jackson.objectmapper;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.jackson.objectmapper.dto.Car;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonToJavaExample extends Example
{
    protected final Logger Logger = LoggerFactory.getLogger(getClass());

    public JsonToJavaExample() { }

    @Override
    public String name()
    {
        return this.getClass().getName();
    }

    @Override
    @Test
    public void testExample() throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper();
        final Car car = objectMapper.readValue(EXAMPLE_JSON, Car.class);
        assertNotNull(car);
        assertThat(car.getColor(), containsString("Black"));
    }
}
