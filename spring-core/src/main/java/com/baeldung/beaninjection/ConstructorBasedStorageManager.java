package com.baeldung.beaninjection;

import com.baeldung.storage.KeyValuePairStorage;

public class ConstructorBasedStorageManager {

	private KeyValuePairStorage storage;

	public ConstructorBasedStorageManager(KeyValuePairStorage storage) {
		this.storage = storage;
	}

	public void store(String key, String value) {
		storage.addEntry(key, value);
	}
}