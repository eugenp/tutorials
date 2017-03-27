package com.baeldung.jackson.deserialization;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import com.baeldung.jackson.entities.MyPair;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonMapDeserializeTest {

<<<<<<< HEAD
	private Map<MyPair, String> map;
=======
	// @JsonDeserialize(keyUsing = MyPairDeserializer.class)
	private Map<MyPair, String> map;

>>>>>>> 946a7e663d8fa38836bf33bb04a975aad72d7454
	private Map<MyPair, MyPair> cmap;

	@Test
	public void whenSimpleMapDeserialize_thenCorrect()
			throws JsonParseException, JsonMappingException, IOException {

		final String jsonInput = "{\"key\": \"value\"}";
		final ObjectMapper mapper = new ObjectMapper();
		TypeReference<HashMap<String, String>> typeRef = new TypeReference<HashMap<String, String>>() {
		};

		final Map<String, String> map = mapper.readValue(jsonInput, typeRef);
		final String expectedOutput = "{key=value}";
		Assert.assertEquals(expectedOutput, map.toString());
	}

	@Test
	public void whenObjectStringMapDeserialize_thenCorrect()
			throws JsonParseException, JsonMappingException, IOException {

		final String jsonInput = "{\"Abbott and Costello\" : \"Comedy\"}";
		final ObjectMapper mapper = new ObjectMapper();

		TypeReference<HashMap<MyPair, String>> typeRef = new TypeReference<HashMap<MyPair, String>>() {
		};
		map = mapper.readValue(jsonInput, typeRef);

		Assert.assertEquals("Abbott and Costello",
				StringUtils.join(map.keySet(), ", "));
	}

	@Test
	public void whenObjectObjectMapDeserialize_thenCorrect()
			throws JsonParseException, JsonMappingException, IOException {

		final String jsonInput = "{\"Abbott and Costello\" : \"Comedy and 1940s\"}";
		final ObjectMapper mapper = new ObjectMapper();
		TypeReference<HashMap<MyPair, MyPair>> typeRef = new TypeReference<HashMap<MyPair, MyPair>>() {
		};

		cmap = mapper.readValue(jsonInput, typeRef);

		Assert.assertEquals("Abbott and Costello",
				StringUtils.join(cmap.keySet(), ", "));
		Assert.assertEquals("Comedy and 1940s",
				StringUtils.join(cmap.values(), ", "));
	}
}
