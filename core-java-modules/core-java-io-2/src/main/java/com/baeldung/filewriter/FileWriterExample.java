package com.baeldung.filewriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileWriterExample {

    public void writeString(String fileName, String strToWrite) throws IOException {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(strToWrite);
        }
    }

    public void appendString(String fileName, String stringToAppend) throws IOException {
        try (FileWriter fileWriter = new FileWriter(fileName, true)) {
            fileWriter.append(stringToAppend);
        }
    }

    public void writeCharArray(String fileName, char[] charArrToWrite) throws IOException {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(charArrToWrite);
        }
    }

    public void writeWithBufferedWriter(String fileName, List<String> stringsToWrite) throws IOException {
        try (FileWriter fileWriter = new FileWriter(fileName); BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            for (String stringToWrite : stringsToWrite) {
                bufferedWriter.write(stringToWrite);
            }
        }

    }

}
