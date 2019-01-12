package com.baeldung.csv;

public class WriteCsvFileExample {

    public String convertToCSV(String[] data) {
        StringBuilder csvLine = new StringBuilder();

        for (int i = 0; i < data.length; i++) {
            if (i > 0) {
                csvLine.append(",");
            }
            csvLine.append(escapeSpecialCharacters(data[i]));
        }

        return csvLine.toString();
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
