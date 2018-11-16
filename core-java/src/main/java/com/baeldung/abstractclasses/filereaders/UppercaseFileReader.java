package com.baeldung.abstractclasses.filereaders;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class UppercaseFileReader extends BaseFileReader {
  
    public UppercaseFileReader(String filePath) {
        super(filePath);
    }
    
    @Override
    public List<String> mapFileLines() throws IOException {
        return this.readFile().
            stream().map(line -> line.toLowerCase()).collect(Collectors.toList());
    }   
}
