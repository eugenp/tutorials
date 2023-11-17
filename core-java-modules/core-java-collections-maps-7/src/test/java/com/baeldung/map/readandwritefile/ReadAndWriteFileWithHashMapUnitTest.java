package com.baeldung.map.readandwritefile;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.After;
import org.junit.Test;
import org.junit.Before;

public class ReadAndWriteFileWithHashMapUnitTest {

    private static final Map<Integer, Student> STUDENT_DATA = new HashMap<>();

    static {
        STUDENT_DATA.put(1234, new Student("Henry", "Winter"));
        STUDENT_DATA.put(5678, new Student("Richard", "Papen"));
    }

    private File file;

    @Before
    public void createFile() throws IOException {
        file = File.createTempFile("student", ".data");
    }

    @After
    public void deleteFile() {
        file.delete();
    }

    @Test
    public void givenHashMap_whenWrittenAsPropertiesFile_thenReloadedMapIsIdentical() throws IOException {
        // Given a map containing student data
        Map<String, String> studentData = new HashMap<>();
        studentData.put("student.firstName", "Henry");
        studentData.put("student.lastName", "Winter");

        // When converting to a Properties object and writing to a file
        Properties props = new Properties();
        props.putAll(studentData);
        try (OutputStream output = Files.newOutputStream(file.toPath())) {
            props.store(output, null);
        }

        // Then the map resulting from loading the Properties file is identical
        Properties propsFromFile = new Properties();
        try (InputStream input = Files.newInputStream(file.toPath())) {
            propsFromFile.load(input);
        }

        Map<String, String> studentDataFromProps = propsFromFile.stringPropertyNames()
            .stream()
            .collect(Collectors.toMap(key -> key, props::getProperty));
        assertThat(studentDataFromProps).isEqualTo(studentData);
    }

    @Test
    public void givenHashMap_whenSerializedToFile_thenDeserializedMapIsIdentical() throws IOException, ClassNotFoundException {
        // Given a map containing student data (STUDENT_DATA)

        // When serializing the map to a file
        try (FileOutputStream fileOutput = new FileOutputStream(file); ObjectOutputStream objectStream = new ObjectOutputStream(fileOutput)) {
            objectStream.writeObject(STUDENT_DATA);
        }

        // Then read the file back into a map and check the contents
        Map<Integer, Student> studentsFromFile;
        try (FileInputStream fileReader = new FileInputStream(file); ObjectInputStream objectStream = new ObjectInputStream(fileReader)) {
            studentsFromFile = (HashMap<Integer, Student>) objectStream.readObject();
        }
        assertThat(studentsFromFile).isEqualTo(STUDENT_DATA);
    }

    @Test
    public void givenHashMap_whenSerializedToFileWithJackson_thenDeserializedMapIsIdentical() throws IOException {
        // Given a map containing student data (STUDENT_DATA)

        // When converting to JSON with Jackson and writing to a file
        ObjectMapper mapper = new ObjectMapper();
        try (FileOutputStream fileOutput = new FileOutputStream(file)) {
            mapper.writeValue(fileOutput, STUDENT_DATA);
        }

        // Then deserialize the file back into a map and check that it's identical
        Map<Integer, Student> mapFromFile;
        try (FileInputStream fileInput = new FileInputStream(file)) {
            // Create a TypeReference so we can deserialize the parameterized type
            TypeReference<HashMap<Integer, Student>> mapType = new TypeReference<HashMap<Integer, Student>>() {
            };
            mapFromFile = mapper.readValue(fileInput, mapType);
        }
        assertThat(mapFromFile).isEqualTo(STUDENT_DATA);
    }

    @Test
    public void givenHashMap_whenSerializedToFileWithGson_thenDeserializedMapIsIdentical() throws IOException {
        // Given a map containing student data (STUDENT_DATA)

        // When converting to JSON using Gson and writing to a file
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(STUDENT_DATA, writer);
        }

        // Then deserialize the file back into a map and check that it's identical
        Map<Integer, Student> studentsFromFile;
        try (FileReader reader = new FileReader(file)) {
            Type mapType = new TypeToken<HashMap<Integer, Student>>() {
            }.getType();
            studentsFromFile = gson.fromJson(reader, mapType);
        }
        assertThat(studentsFromFile).isEqualTo(STUDENT_DATA);
    }
}
