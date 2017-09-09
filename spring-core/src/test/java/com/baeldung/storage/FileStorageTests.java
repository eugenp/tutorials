package com.baeldung.storage;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.junit.Test;

import com.baeldung.storage.FileStorage;

public class FileStorageTests {

	File file = new File("src/main/resources/storageData.properties");

	@Test
	public void addEntry_KeyAndValue_VerifyEntryPresentInFile()
			throws IOException {
		String key = "testKey";
		String value = "testValue";

		FileStorage storage = new FileStorage();
		storage.addEntry(key, value);

		FileInputStream in = null;
		FileOutputStream out = null;

		try {
			in = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(in);

			assertEquals(properties.get(key), value);

			out = new FileOutputStream(file);
			properties.remove(key);
			properties.store(out, null);

		} finally {
			in.close();
			out.close();
		}
	}
}