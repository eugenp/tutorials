package com.baeldung.jackson.objectmapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.jackson.objectmapper.dto.Car;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class JsonParserExample extends Example {

    protected final Logger Logger = LoggerFactory.getLogger(getClass());

    public JsonParserExample() { }

    @Override
    public String name()
    {
        return this.getClass().getName();
    }

    @Override
    public void execute()
    {
        Logger.debug("Executing: "+name());
        final String carJson = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";
        final JsonFactory factory = new JsonFactory();
        JsonParser parser;
        try
        {
            final Car car = new Car();
            parser = factory.createParser(carJson);
            while(!parser.isClosed())
            {
                JsonToken jsonToken = parser.nextToken();
                Logger.debug("jsonToken = " + jsonToken);

                if(JsonToken.FIELD_NAME.equals(jsonToken)){
                    final String fieldName = parser.getCurrentName();
                    System.out.println(fieldName);

                    jsonToken = parser.nextToken();

                    if("color".equals(fieldName)){
                        car.setColor(parser.getValueAsString());
                    } else if ("type".equals(fieldName)){
                        car.setType(parser.getValueAsString());
                    }
                }
            }
            Logger.debug("car:"+car.getColor());
        }
        catch (final Exception e)
        {
            Logger.error(e.toString());
        }
    }
}
