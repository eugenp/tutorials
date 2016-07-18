package com.baeldung.jackson.objectmapper;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.jackson.objectmapper.dto.Car;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class JsonAdvancedCustomSerializeExample extends Example
{

    protected final Logger Logger = LoggerFactory.getLogger(getClass());

    public JsonAdvancedCustomSerializeExample() { }

    @Override
    public String name()
    {
        return this.getClass().getName();
    }

    @Override
    public void testExample() throws Exception {
        final ObjectMapper mapper = new ObjectMapper();
        final SimpleModule serializerModule = new SimpleModule("CustomSerializer", new Version(1, 0, 0, null, null, null));
        serializerModule.addSerializer(Car.class, new CustomCarSerializer());
        mapper.registerModule(serializerModule);
        final Car car = new Car("yellow", "renault");
        final String carJson = mapper.writeValueAsString(car);
        assertThat(carJson, containsString("renault"));
        assertThat(carJson, containsString("model"));

        final SimpleModule deserializerModule = new SimpleModule("CustomCarDeserializer", new Version(1, 0, 0, null, null, null));
        deserializerModule.addDeserializer(Car.class, new CustomCarDeserializer());
        mapper.registerModule(deserializerModule);
        final Car carResult = mapper.readValue(EXAMPLE_JSON, Car.class);
        assertNotNull(carResult);
        assertThat(carResult.getColor(), equalTo("Black"));
    }
}
