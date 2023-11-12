package com.baeldung.xml.xml2csv;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.transform.TransformerException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class Xml2CsvExampleUnitTest {

    private static final String BASE_PATH = "src/main/resources/xml2csv/";

    private static final String STYLE_XSL = BASE_PATH + "style.xsl";
    private static final String DATA_XML = BASE_PATH + "data.xml";
    private static final String TEMP_OUTPUT_CSV = BASE_PATH + "tempOutput.xml";

    @AfterEach
    public void teardown() {
        new File(TEMP_OUTPUT_CSV).delete();
    }

    @Test
    public void whenConvertXml2CsvXslt_thenCsvFileIsCreated() throws IOException, TransformerException {
        Xml2CsvExample.convertXml2CsvXslt(STYLE_XSL, DATA_XML, TEMP_OUTPUT_CSV);

        File csvFile = new File(TEMP_OUTPUT_CSV);
        assertTrue(csvFile.exists());
    }

    @Test
    public void whenConvertXml2CsvStax_thenCsvFileIsCreated() throws IOException, TransformerException {
        Xml2CsvExample.convertXml2CsvStax(DATA_XML, TEMP_OUTPUT_CSV);

        File csvFile = new File(TEMP_OUTPUT_CSV);
        assertTrue(csvFile.exists());
    }

    @Test
    public void whenConvertXml2CsvXslt_thenCsvFileIsNotEmpty() throws IOException, TransformerException {
        Xml2CsvExample.convertXml2CsvXslt(STYLE_XSL, DATA_XML, TEMP_OUTPUT_CSV);

        File csvFile = new File(TEMP_OUTPUT_CSV);
        BufferedReader reader = new BufferedReader(new FileReader(csvFile));
        String firstLine = reader.readLine();
        assertNotNull(firstLine);
        assertFalse(firstLine.isEmpty());

        reader.close();
    }

    @Test
    public void whenConvertXml2CsvStax_thenCsvFileIsNotEmpty() throws IOException, TransformerException {
        Xml2CsvExample.convertXml2CsvStax(DATA_XML, TEMP_OUTPUT_CSV);

        File csvFile = new File(TEMP_OUTPUT_CSV);
        BufferedReader reader = new BufferedReader(new FileReader(csvFile));
        String firstLine = reader.readLine();
        assertNotNull(firstLine);
        assertFalse(firstLine.isEmpty());

        reader.close();
    }

    @Test
    public void whenConvertXml2CsvXsltWithWrongXSL_thenThrowsException() {
        String xslWrongPath = BASE_PATH + "wrongFile.xsl";

        assertThrows(TransformerException.class, () -> Xml2CsvExample.convertXml2CsvXslt(xslWrongPath, DATA_XML, TEMP_OUTPUT_CSV));
    }

    @Test
    public void whenConvertXml2CsvXslt_thenCsvMatchesPattern() throws IOException, TransformerException {
        String headerPattern = "^bookstore_id,book_id,category,title,author_id,author_name,price$";
        String dataPattern = "^[A-Z0-9]+,[A-Z0-9]+,[a-zA-Z]+,[a-zA-Z0-9\\s]+,[A-Z0-9]+,[a-zA-Z\\s]+,\\d+(\\.\\d{2})?$";

        Xml2CsvExample.convertXml2CsvXslt(STYLE_XSL, DATA_XML, TEMP_OUTPUT_CSV);

        File csvFile = new File(TEMP_OUTPUT_CSV);
        try(BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    assertTrue(line.matches(headerPattern), "Header does not match pattern");
                    isFirstLine = false;
                } else {
                    assertTrue(line.matches(dataPattern), "Data line does not match pattern");
                }
            }
        }
    }

    @Test
    public void whenConvertXml2Stax_thenCsvMatchesPattern() throws IOException, TransformerException {
        String headerPattern = "^bookstore_id,book_id,category,title,author_id,author_name,price$";
        String dataPattern = "^[A-Z0-9]+,[A-Z0-9]+,[a-zA-Z]+,[a-zA-Z0-9\\s]+,[A-Z0-9]+,[a-zA-Z\\s]+,\\d+(\\.\\d{2})?$";

        Xml2CsvExample.convertXml2CsvStax(DATA_XML, TEMP_OUTPUT_CSV);

        File csvFile = new File(TEMP_OUTPUT_CSV);
        try(BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    assertTrue(line.matches(headerPattern), "Header does not match pattern");
                    isFirstLine = false;
                } else {
                    assertTrue(line.matches(dataPattern), "Data line does not match pattern");
                }
            }
        }
    }

    @Test
    public void whenConcurrentConversion_thenNoErrors() throws InterruptedException {
        int numThreads = 10;
        ExecutorService service = Executors.newFixedThreadPool(numThreads);
        CountDownLatch latch = new CountDownLatch(numThreads);

        for (int i = 0; i < numThreads; i++) {
            final int threadId = i;
            service.execute(() -> {
                String threadSpecificOutputCsv = BASE_PATH + "tempOutput" + threadId + ".csv";
                try {
                    Xml2CsvExample.convertXml2CsvXslt(STYLE_XSL, DATA_XML, threadSpecificOutputCsv);
                    assertTrue(Files.exists(Paths.get(threadSpecificOutputCsv)), "File should exist");
                } catch (IOException | TransformerException e) {
                    fail("Exception should not be thrown: " + e.getMessage());
                } finally {
                    new File(threadSpecificOutputCsv).delete();
                    latch.countDown();
                }
            });
        }

        latch.await();
    }

}
