package com.baeldung.commons.io.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class CSVReaderWriterUnitTest {

    public static final Map<String, String> AUTHOR_BOOK_MAP = Collections.unmodifiableMap(new LinkedHashMap<String, String>() {
        {
            put("Dan Simmons", "Hyperion");
            put("Douglas Adams", "The Hitchhiker's Guide to the Galaxy");
        }
    });
    public static final String[] HEADERS = { "author", "title" };
    public static final String EXPECTED_FILESTREAM = "author,title\r\n" + "Dan Simmons,Hyperion\r\n" + "Douglas Adams,The Hitchhiker's Guide to the Galaxy";

    @Test
    public void givenCSVFile_whenRead_thenContentsAsExpected() throws IOException {
        Reader in = new FileReader("src/test/resources/book.csv");
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader(HEADERS).withFirstRecordAsHeader().parse(in);
        for (CSVRecord record : records) {
            String author = record.get("author");
            String title = record.get("title");
            assertEquals(AUTHOR_BOOK_MAP.get(author), title);
        }
    }

    @Test
    public void givenAuthorBookMap_whenWrittenToStream_thenOutputStreamAsExpected() throws IOException {
        StringWriter sw = new StringWriter();
        try (final CSVPrinter printer = new CSVPrinter(sw, CSVFormat.DEFAULT.withHeader(HEADERS))) {
            AUTHOR_BOOK_MAP.forEach((author, title) -> {
                try {
                    printer.printRecord(author, title);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        assertEquals(EXPECTED_FILESTREAM, sw.toString().trim());
    }

}
