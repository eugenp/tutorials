package com.baeldung.serialization;

import java.io.IOException;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ObjectIdSerializer
extends JsonSerializer<ObjectId>
{
	@Override
	public void serialize(ObjectId objectId, JsonGenerator json, SerializerProvider provider)
	throws IOException, JsonProcessingException
	{
		json.writeString(objectId.toString());
	}
}
