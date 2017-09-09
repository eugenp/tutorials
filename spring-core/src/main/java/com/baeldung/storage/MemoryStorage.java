package com.baeldung.storage;

import java.util.HashMap;
import java.util.Map;

public class MemoryStorage implements KeyValuePairStorage {

	private Map<String, String> memoryStorage = new HashMap<String, String>();

	public void addEntry(String key, String value) {
		memoryStorage.put(key, value);
	}
}