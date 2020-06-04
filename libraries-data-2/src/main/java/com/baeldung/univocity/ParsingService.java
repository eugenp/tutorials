package com.baeldung.univocity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.univocity.model.Product;
import com.univocity.parsers.common.processor.BatchedColumnProcessor;
import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import com.univocity.parsers.fixed.FixedWidthFields;
import com.univocity.parsers.fixed.FixedWidthParser;
import com.univocity.parsers.fixed.FixedWidthParserSettings;

public class ParsingService {
    private Logger logger = LoggerFactory.getLogger(ParsingService.class);

    public List<String[]> parseCsvFile(String relativePath) {
        try (Reader inputReader = new InputStreamReader(new FileInputStream(new File(relativePath)), "UTF-8")) {
            CsvParserSettings settings = new CsvParserSettings();
            settings.setMaxCharsPerColumn(100);
            settings.setMaxColumns(50);
            CsvParser parser = new CsvParser(settings);
            List<String[]> parsedRows = parser.parseAll(inputReader);
            return parsedRows;
        } catch (IOException e) {
            logger.error("IOException opening file: " + relativePath + " " + e.getMessage());
            return new ArrayList<String[]>();
        }
    }

    public List<String[]> parseFixedWidthFile(String relativePath) {
        try (Reader inputReader = new InputStreamReader(new FileInputStream(new File(relativePath)), "UTF-8")) {
            FixedWidthFields fieldLengths = new FixedWidthFields(8, 30, 10);
            FixedWidthParserSettings settings = new FixedWidthParserSettings(fieldLengths);

            FixedWidthParser parser = new FixedWidthParser(settings);
            List<String[]> parsedRows = parser.parseAll(inputReader);
            return parsedRows;
        } catch (IOException e) {
            logger.error("IOException opening file: " + relativePath + " " + e.getMessage());
            return new ArrayList<String[]>();
        }
    }

    public List<Product> parseCsvFileIntoBeans(String relativePath) {
        try (Reader inputReader = new InputStreamReader(new FileInputStream(new File(relativePath)), "UTF-8")) {
            BeanListProcessor<Product> rowProcessor = new BeanListProcessor<Product>(Product.class);
            CsvParserSettings settings = new CsvParserSettings();
            settings.setHeaderExtractionEnabled(true);
            settings.setProcessor(rowProcessor);
            CsvParser parser = new CsvParser(settings);
            parser.parse(inputReader);
            return rowProcessor.getBeans();
        } catch (IOException e) {
            logger.error("IOException opening file: " + relativePath + " " + e.getMessage());
            return new ArrayList<Product>();
        }
    }

    public List<String[]> parseCsvFileInBatches(String relativePath) {
        try (Reader inputReader = new InputStreamReader(new FileInputStream(new File(relativePath)), "UTF-8")) {
            CsvParserSettings settings = new CsvParserSettings();
            settings.setProcessor(new BatchedColumnProcessor(5) {
                @Override
                public void batchProcessed(int rowsInThisBatch) {
                }
            });
            CsvParser parser = new CsvParser(settings);
            List<String[]> parsedRows = parser.parseAll(inputReader);
            return parsedRows;
        } catch (IOException e) {
            logger.error("IOException opening file: " + relativePath + " " + e.getMessage());
            return new ArrayList<String[]>();
        }
    }
}
