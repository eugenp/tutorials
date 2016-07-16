package com.baeldung.jackson.objectmapper;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.jackson.objectmapper.dto.Car;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JavaToJsonExample extends Example
{

    protected final Logger Logger = LoggerFactory.getLogger(getClass());

    public JavaToJsonExample() { }

    @Override
    public String name()
    {
        return this.getClass().getName();
    }

    @Override
    public void execute()
    {
        try
        {
            final ObjectMapper objectMapper = new ObjectMapper();
            final Car car = new Car("yellow", "renault");
            final String carAsString = objectMapper.writeValueAsString(car);
            Logger.debug(carAsString);
        }
        catch(final Exception e)
        {
            Logger.error(e.toString());
        }
    }

    @Override
    @Test
    public void test() throws Exception {
        final ObjectMapper objectMapper = new ObjectMapper();
        final Car car = new Car("yellow", "renault");
        final String carAsString = objectMapper.writeValueAsString(car);
        assertThat(carAsString, containsString("yellow"));
        assertThat(carAsString, containsString("renault"));
    }
}
