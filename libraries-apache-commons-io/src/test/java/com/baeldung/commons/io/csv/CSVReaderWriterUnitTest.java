package com.baeldung.commons.io.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

class CSVReaderWriterUnitTest {

    public static final Map<String, String> AUTHOR_BOOK_MAP = Collections.unmodifiableMap(new LinkedHashMap<String, String>() {
        {
            put("Dan Simmons", "Hyperion");
            put("Douglas Adams", "The Hitchhiker's Guide to the Galaxy");
        }
    });
    public static final String[] HEADERS = { "author", "title" };

    enum BookHeaders{
        author, title
    }

    public static final String EXPECTED_FILESTREAM = "author,title\r\n" + "Dan Simmons,Hyperion\r\n" + "Douglas Adams,The Hitchhiker's Guide to the Galaxy";

    @Test
    void givenCSVFile_whenReadWithArrayHeader_thenContentsAsExpected() throws IOException {
        Reader in = new FileReader("src/test/resources/book.csv");

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
            .setHeader(HEADERS)
            .setSkipHeaderRecord(true)
            .build();

        Iterable<CSVRecord> records = csvFormat.parse(in);

        for (CSVRecord record : records) {
            String author = record.get("author");
            String title = record.get("title");
            assertEquals(AUTHOR_BOOK_MAP.get(author), title);
        }
    }

    @Test
    void givenCSVFile_whenReadWithEnumHeader_thenContentsAsExpected() throws IOException {
        Reader in = new FileReader("src/test/resources/book.csv");

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
            .setHeader(BookHeaders.class)
            .setSkipHeaderRecord(true)
            .build();

        Iterable<CSVRecord> records = csvFormat.parse(in);

        for (CSVRecord record : records) {
            String author = record.get(BookHeaders.author);
            String title = record.get(BookHeaders.title);
            assertEquals(AUTHOR_BOOK_MAP.get(author), title);
        }
    }

    @Test
    void givenAuthorBookMap_whenWrittenToStream_thenOutputStreamAsExpected() throws IOException {
        StringWriter sw = new StringWriter();

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
            .setHeader(BookHeaders.class)
            .build();

        try (final CSVPrinter printer = new CSVPrinter(sw, csvFormat)) {
            AUTHOR_BOOK_MAP.forEach((author, title) -> {
                try {
                    printer.printRecord(author, title);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        assertEquals(EXPECTED_FILESTREAM, sw.toString()
            .trim());
    }

}
