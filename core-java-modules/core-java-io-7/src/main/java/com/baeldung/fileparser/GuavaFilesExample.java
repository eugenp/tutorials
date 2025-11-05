package com.baeldung.fileparser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.google.common.io.Files;

public class GuavaFilesExample {

    static ArrayList<String> generateArrayListFromFile(String filename) throws IOException {
        List<String> result = Files.readLines(new File(filename), Charset.forName("utf-8"));

        return new ArrayList<>(result);
    }

}
