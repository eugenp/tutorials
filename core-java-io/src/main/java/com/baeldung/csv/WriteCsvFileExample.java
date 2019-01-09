package com.baeldung.csv;

import java.io.BufferedWriter;
import java.io.IOException;

public class WriteCsvFileExample {

    public void writeLine(BufferedWriter writer, String[] data) throws IOException {
        StringBuilder csvLine = new StringBuilder();

        for (int i = 0; i < data.length; i++) {
            if (i > 0) {
                csvLine.append(",");
            }
            csvLine.append(escapeSpecialCharacters(data[i]));
        }

        writer.write(csvLine.toString());
    }

    public String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }
}
