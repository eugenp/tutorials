package com.baeldung.tika;

import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.Tika;
import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.detect.Detector;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class TikaAnalysis {
    public static String detectDocTypeUsingDetector(InputStream stream) throws IOException {
        Detector detector = new DefaultDetector();
        Metadata metadata = new Metadata();

        MediaType mediaType = detector.detect(stream, metadata);
        return mediaType.toString();
    }

    public static String detectDocTypeUsingFacade(InputStream stream) throws IOException {
        Tika tika = new Tika();
        String mediaType = tika.detect(stream);
        return mediaType;
    }

    public static String extractContentUsingParser(InputStream stream) throws IOException, TikaException, SAXException {
        Parser parser = new AutoDetectParser();
        ContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext context = new ParseContext();

        parser.parse(stream, handler, metadata, context);
        return handler.toString();
    }

    public static String extractContentUsingFacade(InputStream stream) throws IOException, TikaException {
        Tika tika = new Tika();
        String content = tika.parseToString(stream);
        return content;
    }

    public static Metadata extractMetadatatUsingParser(InputStream stream) throws IOException, SAXException, TikaException {
        Parser parser = new AutoDetectParser();
        ContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext context = new ParseContext();

        parser.parse(stream, handler, metadata, context);
        return metadata;
    }

    public static Metadata extractMetadatatUsingFacade(InputStream stream) throws IOException, TikaException {
        Tika tika = new Tika();
        Metadata metadata = new Metadata();

        tika.parse(stream, metadata);
        return metadata;
    }
}
