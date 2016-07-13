package com.baeldung.jackson.objectmapper;

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

    String json = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";

    @Override
    public String name()
    {
        return this.getClass().getName();
    }

    @Override
    public void execute()
    {
        Logger.debug("Executing: "+name());
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            final SimpleModule module = new SimpleModule("CustomSerializer", new Version(1, 0, 0, null, null, null));
            module.addSerializer(Car.class, new CustomCarSerializer());
            mapper = new ObjectMapper();
            mapper.registerModule(module);
            final Car car = new Car("yellow", "renault");
            final String carJson = mapper.writeValueAsString(car);
            Logger.debug("car as json = " + carJson);
        }
        catch (final Exception e)
        {
            Logger.error(e.toString());
        }
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            final SimpleModule module = new SimpleModule("CustomCarDeserializer", new Version(1, 0, 0, null, null, null));
            module.addDeserializer(Car.class, new CustomCarDeserializer());
            mapper = new ObjectMapper();
            mapper.registerModule(module);
            final Car car = mapper.readValue(json, Car.class);
            Logger.debug("car type  = " + car.getType());
            Logger.debug("car color = " + car.getColor());
        }
        catch (final Exception e)
        {
            Logger.error(e.toString());
        }
    }
}
