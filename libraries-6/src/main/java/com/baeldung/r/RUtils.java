package com.baeldung.r;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

/**
 * Utility class for loading the script.R content.
 * 
 * @author Donato Rimenti
 */
public class RUtils {

    /**
     * Loads the script.R and returns its content as a string.
     * 
     * @return the script.R content as a string
     * @throws IOException        if any error occurs
     * @throws URISyntaxException if any error occurs
     */
    static String getMeanScriptContent() throws IOException, URISyntaxException {
        URI rScriptUri = RUtils.class.getClassLoader()
            .getResource("script.R")
            .toURI();
        Path inputScript = Paths.get(rScriptUri);
        return Files.lines(inputScript)
            .collect(Collectors.joining());
    }
}