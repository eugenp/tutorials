package com.baeldung.commaseparatedstring;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import com.google.common.base.Splitter;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class SplitCommaSeparatedString {

    public static List<String> splitWithParser(String input) {

        List<String> tokens = new ArrayList<String>();
        int startPosition = 0;
        boolean isInQuotes = false;
        for (int currentPosition = 0; currentPosition < input.length(); currentPosition++) {
            if (input.charAt(currentPosition) == '\"') {
                isInQuotes = !isInQuotes;
            } else if (input.charAt(currentPosition) == ',' && !isInQuotes) {
                tokens.add(input.substring(startPosition, currentPosition));
                startPosition = currentPosition + 1;
            }
        }

        String lastToken = input.substring(startPosition);
        if (lastToken.equals(",")) {
            tokens.add("");
        } else {
            tokens.add(lastToken);
        }

        return tokens;
    }

    public static List<String> splitWithRegex(String input) {
        String[] tokens = input.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        return Arrays.asList(tokens);
    }

    public static List<String> splitWithGuava(String input) {
        Pattern pattern = Pattern.compile(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        Splitter splitter = Splitter.on(pattern);
        return splitter.splitToList(input);
    }

    public static List<String[]> splitMultiLineWithOpenCSV(String input) throws IOException {
        CSVParser parser = new CSVParserBuilder().withSeparator(',')
            .build();

        CSVReader reader = new CSVReaderBuilder(new StringReader(input)).withCSVParser(parser)
            .build();

        List<String[]> list = new ArrayList<>();
        list = reader.readAll();
        reader.close();

        return list;
    }
}