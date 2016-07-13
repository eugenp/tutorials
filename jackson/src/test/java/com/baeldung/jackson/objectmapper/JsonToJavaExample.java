package com.baeldung.jackson.objectmapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.jackson.objectmapper.dto.Car;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonToJavaExample extends Example
{
    protected final Logger Logger = LoggerFactory.getLogger(getClass());

    public JsonToJavaExample() { }

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
            final ObjectMapper objectMapper = new ObjectMapper();
            final Car car = objectMapper.readValue(json, Car.class);
            Logger.debug("Color = " + car.getColor());
            Logger.debug("Type = " + car.getType());
        }
        catch (final Exception e)
        {
            Logger.error(e.toString());
        }

        try
        {
            final ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);

            final String jsonCar = "\"car\" : { \"color\" : \"Red\", \"type\" : \"FIAT\" }";
            final Response response = objectMapper.readValue(jsonCar, Response.class);

            Logger.debug("response: "+response);
        }
        catch (final Exception e)
        {
            Logger.error(e.toString());
        }
    }

    class Response {

        Car car;

        public Car getCar() {
            return car;
        }

        public void setCars(final Car car) {
            this.car = car;
        }

    }
}
