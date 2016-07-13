package com.baeldung.jackson.objectmapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonToJsonNode extends Example
{
    protected final Logger Logger = LoggerFactory.getLogger(getClass());

    public JsonToJsonNode() { }

    String jsonString = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";

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
            final JsonNode jsonNode = objectMapper.readTree(jsonString);
            Logger.debug(jsonNode.get("color").asText());
        }
        catch (final Exception e)
        {
            Logger.error(e.toString());
        }
    }
}
