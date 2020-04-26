package com.baeldung.univocity;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;


public class ParsingService {
    Logger logger = LoggerFactory.getLogger(ParsingService.class);

    public List<String[]> parseCsvFile(String relativePath) {
        try (Reader inputReader = new InputStreamReader(this.getClass().getResourceAsStream(relativePath), "UTF-8")) {
            CsvParser parser = new CsvParser(new CsvParserSettings());
            List<String[]> parsedRows = parser.parseAll(inputReader);
            return parsedRows;
        } catch (UnsupportedEncodingException e) {
            logger.error("UnsupportedEncodingException opening file: " + relativePath + " " + e.getMessage());
            return new ArrayList<String[]>();
        } catch (IOException e) {
            logger.error("IOException opening file: " + relativePath + " " + e.getMessage());
            return new ArrayList<String[]>();
        }
    }
}
