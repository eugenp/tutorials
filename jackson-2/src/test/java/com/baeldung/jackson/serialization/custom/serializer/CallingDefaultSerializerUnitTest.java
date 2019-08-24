package com.baeldung.jackson.serialization.custom.serializer;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.baeldung.jackson.entities.File;
import com.baeldung.jackson.entities.Folder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class CallingDefaultSerializerUnitTest {

    private ObjectMapper mapper;
    private Folder mockFolder;
    private TypeReference<HashMap<String, Object>> mapType;

    @Before
    public void setup() {

        mapType = new TypeReference<HashMap<String, Object>>() {
        };

        mapper = new ObjectMapper();

        mockFolder = new Folder();
        mockFolder.setId(1L);
        mockFolder.setName("Root Folder");
        mockFolder.setOwner("root");
        mockFolder.setCreated(Date.from(Instant.now().minusSeconds(60)));
        mockFolder.setModified(Date.from(Instant.now().minusSeconds(30)));
        mockFolder.setLastAccess(Date.from(Instant.now()));

        File file1 = new File();
        file1.setId(1L);
        file1.setName("File 1");

        File file2 = new File();
        file2.setId(2L);
        file2.setName("File 2");

        List<File> files = new ArrayList<>();
        files.add(file1);
        files.add(file2);
        mockFolder.setFiles(files);

    }

    @Test
    public void givenFolder_whenSerialized_onlyNameAndFilesFieldsSerialized() throws IOException {

        SimpleModule module = new SimpleModule();
        module.addSerializer(new FolderSerializer());
        mapper.registerModule(module);

        String json = mapper.writeValueAsString(mockFolder);

        HashMap<String, Object> actual = mapper.readValue(json, mapType);

        assertTrue(actual.containsKey("name"));
        assertTrue(actual.containsKey("files"));
        assertEquals(mockFolder.getName(), actual.get("name"));

        List actualFiles = (List) actual.get("files");
        assertEquals(mockFolder.getFiles().size(), actualFiles.size());
        
    }

    @Test
    public void givenFolder_whenSerializedWithSerializerProvider_onlyNameAndFilesFieldsSerialized() throws IOException {

        SimpleModule module = new SimpleModule();
        module.addSerializer(new FolderSerializerWithSerializerProvider());
        mapper.registerModule(module);

        String json = mapper.writeValueAsString(mockFolder);

        HashMap<String, Object> actual = mapper.readValue(json, mapType);

        assertTrue(actual.containsKey("name"));
        assertTrue(actual.containsKey("files"));
        assertEquals(mockFolder.getName(), actual.get("name"));

        List actualFiles = (List) actual.get("files");
        assertEquals(mockFolder.getFiles().size(), actualFiles.size());

    }

    @Test
    public void givenFolder_whenSerializedWithInternalObjectMapper_onlyNameAndFilesFieldsSerialized() throws IOException {

        SimpleModule module = new SimpleModule();
        module.addSerializer(new FolderSerializerWithInternalObjectMapper());
        mapper.registerModule(module);

        String json = mapper.writeValueAsString(mockFolder);

        HashMap<String, Object> actual = mapper.readValue(json, mapType);

        assertTrue(actual.containsKey("name"));
        assertTrue(actual.containsKey("files"));
        assertEquals(mockFolder.getName(), actual.get("name"));

        List actualFiles = (List) actual.get("files");
        assertEquals(mockFolder.getFiles().size(), actualFiles.size());

    }

    @Test(expected = StackOverflowError.class)
    public void givenFolder_whenSerializedWithCallingOwnSerializer_exceptionOccured() throws IOException {

        SimpleModule module = new SimpleModule();
        module.addSerializer(new FolderSerializerWithCallingOwnSerializer());
        mapper.registerModule(module);

        mapper.writeValueAsString(mockFolder);
        
    }

    @Test
    public void givenFolder_whenSerializedWithDefaultSerializerStored_NameAndFilesAndDetailsFieldsSerialized() throws IOException {

        SimpleModule module = new SimpleModule();
        module.setSerializerModifier(new FolderBeanSerializerModifier());
        mapper.registerModule(module);

        String json = mapper.writeValueAsString(mockFolder);
        
        HashMap<String, Object> actual = mapper.readValue(json, mapType);

        assertTrue(actual.containsKey("name"));
        assertTrue(actual.containsKey("files"));
        assertEquals(mockFolder.getName(), actual.get("name"));

        List actualFiles = (List) actual.get("files");
        assertEquals(mockFolder.getFiles().size(), actualFiles.size());
        
        Map actualDetails = (Map) actual.get("details");
        assertTrue(actualDetails.containsKey("id"));
        assertTrue(actualDetails.containsKey("name"));
        assertTrue(actualDetails.containsKey("owner"));
        assertTrue(actualDetails.containsKey("created"));
        assertTrue(actualDetails.containsKey("modified"));
        assertTrue(actualDetails.containsKey("lastAccess"));
        
        assertEquals(mockFolder.getId().longValue(), ((Number)actualDetails.get("id")).longValue());
        assertEquals(mockFolder.getName(), actualDetails.get("name"));
        assertEquals(mockFolder.getOwner(), actualDetails.get("owner"));
        assertEquals(mockFolder.getCreated(), new Date((long) actualDetails.get("created")));
        assertEquals(mockFolder.getModified(), new Date((long) actualDetails.get("modified")));
        assertEquals(mockFolder.getLastAccess(), new Date((long) actualDetails.get("lastAccess")));

    }

}
