package com.baeldung.core.java.properties;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileAPIPropertyMutator {

    private final String propertyFileName;
    private final PropertyLoader propertyLoader;

    public FileAPIPropertyMutator(String propertyFileName, PropertyLoader propertyLoader) {
        this.propertyFileName = propertyFileName;
        this.propertyLoader = propertyLoader;
    }

    public String getPropertyKeyWithValue(int lineNumber) throws IOException {
        List<String> fileLines = getFileLines();
        // depending on the system, sometimes the first line will be a comment with a timestamp of the file read
        // the next line will make this method compatible with all systems
        if (fileLines.get(0).startsWith("#")) {
            lineNumber++;
        }

        return fileLines.get(lineNumber);
    }

    public String getLastPropertyKeyWithValue() throws IOException {
        List<String> fileLines = getFileLines();

        return fileLines.get(fileLines.size() - 1);
    }

    public void addPropertyKeyWithValue(String keyAndValue) throws IOException {
        File propertiesFile = new File(propertyLoader.getFilePathFromResources(propertyFileName));
        List<String> fileContent = getFileLines(propertiesFile);

        fileContent.add(keyAndValue);
        Files.write(propertiesFile.toPath(), fileContent, StandardCharsets.UTF_8);
    }

    public int updateProperty(String oldKeyValuePair, String newKeyValuePair) throws IOException {
        File propertiesFile = new File(propertyLoader.getFilePathFromResources(propertyFileName));
        List<String> fileContent = getFileLines(propertiesFile);
        int updatedIndex = -1;

        for (int i = 0; i < fileContent.size(); i++) {
            if (fileContent.get(i)
              .replaceAll("\\s+", "")
              .equals(oldKeyValuePair)) {
                fileContent.set(i, newKeyValuePair);
                updatedIndex = i;
                break;
            }
        }
        Files.write(propertiesFile.toPath(), fileContent, StandardCharsets.UTF_8);

        // depending on the system, sometimes the first line will be a comment with a timestamp of the file read
        // the next line will make this method compatible with all systems
        if (fileContent.get(0).startsWith("#")) {
            updatedIndex--;
        }

        return updatedIndex;
    }

    private List<String> getFileLines() throws IOException {
        File propertiesFile = new File(propertyLoader.getFilePathFromResources(propertyFileName));
        return getFileLines(propertiesFile);
    }

    private List<String> getFileLines(File propertiesFile) throws IOException {
        return new ArrayList<>(Files.readAllLines(propertiesFile.toPath(), StandardCharsets.UTF_8));
    }
}
