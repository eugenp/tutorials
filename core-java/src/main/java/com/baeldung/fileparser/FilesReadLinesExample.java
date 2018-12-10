package com.baeldung.fileparser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FilesReadLinesExample {

    protected static ArrayList<String> generateArrayListFromFile(String filename) throws IOException {
        
        List<String> result = Files.readAllLines(Paths.get(filename));
        
        return (ArrayList<String>) result;
    }

}
