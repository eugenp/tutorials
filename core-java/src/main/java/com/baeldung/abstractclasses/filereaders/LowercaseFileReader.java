package com.baeldung.abstractclasses.filereaders;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class LowercaseFileReader extends BaseFileReader {

    public LowercaseFileReader(Path filePath) {
        super(filePath);
    }

    @Override
    public List<String> mapFileLines() throws IOException {
        return this.readFile().
            stream().map(line -> line.toLowerCase()).collect(Collectors.toList());
    }   
}
