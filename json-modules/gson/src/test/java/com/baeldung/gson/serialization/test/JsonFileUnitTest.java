package com.baeldung.gson.serialization.test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.baeldung.gson.entities.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RunWith(Parameterized.class)
public class JsonFileUnitTest {

	@Parameter
	public Object object;

	@Parameters
	public static Object[] data() {
		return new Object[] { 123.45, new User(1, "Tom", "American") };
	}

	@Test
	public void givenProperData_whenStoredInFile_shouldSaveJsonSuccessfully() {
		String filePath = "target/output.json";
		try (Writer writer = new FileWriter(filePath)) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(object, writer);
			Assert.assertTrue(Files.exists(Paths.get(filePath)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
