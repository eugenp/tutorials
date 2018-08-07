package com.baeldung.jsonpointer;

import static org.junit.Assert.*;


import org.junit.Test;


public class JsonPointerCrudTest {
	
	@Test
	public void testJsonPointerCrud() {
		
		JsonPointerCrud jsonPointerCrud = new JsonPointerCrud(JsonPointerCrudTest.class.getResourceAsStream("/address.json"));
		
		assertFalse(jsonPointerCrud.check("city"));
		
		// insert a value
		jsonPointerCrud.insert("city", "Rio de Janeiro");
		
		assertTrue(jsonPointerCrud.check("city"));
		
		
		// fetch full json
		String fullJSON = jsonPointerCrud.fetchFullJSON();
		
		assertTrue(fullJSON.contains("name"));
		
		assertTrue(fullJSON.contains("city"));
		
		// fetch value
		String cityName = jsonPointerCrud.fetchValueFromKey("city");
		
		assertEquals(cityName, "Rio de Janeiro");
		
		// update value
		jsonPointerCrud.update("city", "Sao Paulo");
		
		// fetch value
		cityName = jsonPointerCrud.fetchValueFromKey("city");
		
		assertEquals(cityName, "Sao Paulo");
		
		// delete
		jsonPointerCrud.delete("city");
		
		assertFalse(jsonPointerCrud.check("city"));
		

	}


}