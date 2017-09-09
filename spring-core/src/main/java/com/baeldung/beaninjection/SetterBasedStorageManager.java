package com.baeldung.beaninjection;

import com.baeldung.storage.KeyValuePairStorage;

public class SetterBasedStorageManager {

	private KeyValuePairStorage storage;

	public void setStorage(KeyValuePairStorage storage) {
		this.storage = storage;
	}

	public void store(String key, String value) {
		storage.addEntry(key, value);
	}
}