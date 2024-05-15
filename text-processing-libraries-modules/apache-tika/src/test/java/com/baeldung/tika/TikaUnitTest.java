package com.baeldung.tika;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.junit.Test;
import org.xml.sax.SAXException;

public class TikaUnitTest {
    @Test
    public void whenUsingDetector_thenDocumentTypeIsReturned() throws IOException {
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("tika.txt");
        String mediaType = TikaAnalysis.detectDocTypeUsingDetector(stream);

        assertEquals("application/pdf", mediaType);

        stream.close();
    }

    @Test
    public void whenUsingFacade_thenDocumentTypeIsReturned() throws IOException {
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("tika.txt");
        String mediaType = TikaAnalysis.detectDocTypeUsingFacade(stream);

        assertEquals("application/pdf", mediaType);

        stream.close();
    }

    @Test
    public void whenUsingParser_thenContentIsReturned() throws IOException, TikaException, SAXException {
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("tika.docx");
        String content = TikaAnalysis.extractContentUsingParser(stream);

        assertThat(content, containsString("Apache Tika - a content analysis toolkit"));
        assertThat(content, containsString("detects and extracts metadata and text"));

        stream.close();
    }

    @Test
    public void whenUsingFacade_thenContentIsReturned() throws IOException, TikaException {
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("tika.docx");
        String content = TikaAnalysis.extractContentUsingFacade(stream);

        assertThat(content, containsString("Apache Tika - a content analysis toolkit"));
        assertThat(content, containsString("detects and extracts metadata and text"));

        stream.close();
    }

    @Test
    public void whenUsingParser_thenMetadataIsReturned() throws IOException, TikaException, SAXException {
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("tika.xlsx");
        Metadata metadata = TikaAnalysis.extractMetadatatUsingParser(stream);

        assertEquals("org.apache.tika.parser.DefaultParser", metadata.get("X-Parsed-By"));
        assertEquals("Microsoft Office User", metadata.get("Author"));

        stream.close();
    }

    @Test
    public void whenUsingFacade_thenMetadataIsReturned() throws IOException, TikaException {
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("tika.xlsx");
        Metadata metadata = TikaAnalysis.extractMetadatatUsingFacade(stream);

        assertEquals("org.apache.tika.parser.DefaultParser", metadata.get("X-Parsed-By"));
        assertEquals("Microsoft Office User", metadata.get("Author"));

        stream.close();
    }
}
