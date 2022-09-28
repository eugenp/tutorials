package com.baeldung.filewriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class BufferedWriterExample {

    public static String generateFileFromArrayList(ArrayList<String> stringArrayList) throws IOException {
        final String TEXT_FILENAME = "src/test/resources/sampleTextFile.txt";
        BufferedWriter br = new BufferedWriter(new FileWriter(TEXT_FILENAME));
        for (String str : stringArrayList) {
            br.write(str + System.lineSeparator());
        }
        br.close();
        return TEXT_FILENAME;
    }
}
