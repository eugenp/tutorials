package com.baeldung.obtainlastsegmentofurl;

import org.apache.commons.io.FilenameUtils;

import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ObtainLastSegmentOfURLUnitTest {
    @Test
    public void givenURL_whenUsingURIClass_thenGetLastPathSegment() throws URISyntaxException {
        URI uri = new URI("https://www.example.com/path/to/resource");
        String path = uri.getPath();

        String[] segments = path.split("/");
        String lastSegment = segments[segments.length - 1];

        assertEquals("resource", lastSegment);
    }

    @Test
    public void givenURL_whenUsingPathClass_thenGetLastPathSegment() {
        String exampleURI = "https://www.example.com/path/to/resource";

        try {
            URI uri = new URI(exampleURI);
            String pathString = uri.getPath();
            Path path = Paths.get(pathString);
            Path lastSegment = path.getName(path.getNameCount() - 1);

            assertEquals("resource", lastSegment.toString());
        } catch (Exception e) {
            fail("Exception occurred: " + e.getMessage());
        }
    }

    @Test
    public void givenURL_whenUsingRegularExpression_thenGetLastPathSegment() throws URISyntaxException {
        URI uri = new URI("https://www.example.com/path/to/resource");
        String path = uri.getPath();

        Pattern pattern = Pattern.compile(".*/(.+)");
        Matcher matcher = pattern.matcher(path);

        if (!matcher.find()) {
            fail("Regex pattern didn't match.");
        }

        String lastSegment = matcher.group(1);
        assertEquals("resource", lastSegment);
    }

    @Test
    public void givenURL_whenUsingFilenameUtilsClass_thenGetLastPathSegment() throws URISyntaxException {
        String exampleURI = "https://www.example.com/path/to/resource";

        URI uri = new URI(exampleURI);
        String path = uri.getPath();

        String lastSegment = FilenameUtils.getName(path);

        assertEquals("resource", lastSegment);
    }
}
