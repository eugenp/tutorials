package com.baeldung.abstractclasses.filereaders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class LowercaseFileReader extends BaseFileReader {

    public LowercaseFileReader(String filePath) {
        super(filePath);
    }
    
    @Override
    public List<String> readFile() throws IOException {
        return Files.lines(Paths.get(filePath))
            .map(String::toLowerCase)
            .collect(Collectors.toList());
    }
}
