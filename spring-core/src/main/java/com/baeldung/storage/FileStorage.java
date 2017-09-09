package com.baeldung.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class FileStorage implements KeyValuePairStorage {

	Properties properties;
	File storageFile = new File("src/main/resources/storageData.properties");

	public FileStorage() throws IOException {
		properties = new Properties();
		storageFile.createNewFile();
		try (FileInputStream str = new FileInputStream(storageFile)) {
			properties.load(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addEntry(String key, String value) {
		properties.put(key, value);
		try (FileOutputStream outStr = new FileOutputStream(storageFile)) {
			properties.store(outStr, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}