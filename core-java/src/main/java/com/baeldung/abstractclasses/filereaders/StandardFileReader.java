package com.baeldung.abstractclasses.filereaders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class StandardFileReader extends BaseFileReader {

    public StandardFileReader(String filePath) {
        super(filePath);
    }
    
    @Override
    public List<String> readFile() throws IOException {
        return Files.lines(Paths.get(filePath)).collect(Collectors.toList());
    }
}
