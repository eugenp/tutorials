package com.baeldung.abstractclasses.filereaders;

import java.io.IOException;
import java.util.List;

public abstract class BaseFileReader {
    
    protected String filePath;
    
    protected BaseFileReader(String filePath) {
        this.filePath = filePath;
    }
    
    public String getFilePath() {
        return filePath;
    }
    
    public abstract List<String> readFile() throws IOException;
}
