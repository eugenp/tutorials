package com.baeldung.copydirectory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JavaNio {

    public static void copyDirectory(String sourceDirectoryLocation, String destinationDirectoryLocation) throws IOException {
        Files.walk(Paths.get(sourceDirectoryLocation))
            .forEach(source -> {
                Path destination = Paths.get(destinationDirectoryLocation, source.toString()
                    .substring(sourceDirectoryLocation.length()));
                try {
                    Files.copy(source, destination);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
    }
}
