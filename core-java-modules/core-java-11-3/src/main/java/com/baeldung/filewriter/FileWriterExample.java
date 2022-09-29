package com.baeldung.filewriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileWriterExample {

    public static String generateFileFromStringList(List<String> stringList) throws IOException {
        final String TEXT_FILENAME = "src/test/resources/sampleTextFile.txt";
        FileWriter fileWriter = new FileWriter(TEXT_FILENAME);
        for (String str : stringList) {
            fileWriter.write(str + System.lineSeparator());
        }
        fileWriter.close();
        return TEXT_FILENAME;
    }
}
