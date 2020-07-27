package com.baeldung.copyfolder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JavaNio {

    public static void copyDirectory(String sourceDirectoryLocation, String destinationDirectoryLocation) throws IOException {
        Files.walk(Paths.get(sourceDirectoryLocation))
            .forEach(a -> {
                Path b = Paths.get(destinationDirectoryLocation, a.toString()
                    .substring(sourceDirectoryLocation.length()));
                try {
                    Files.copy(a, b);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
    }
}
