package com.baeldung.lob;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.ClassPathResource;

class ResourceReader {

    static byte[] readResourceAsBytes(String resource) throws IOException {
        InputStream catImage = new ClassPathResource(resource).getInputStream();
        byte[] image = new byte[catImage.available()];
        catImage.read(image);
        return image;
    }

    static File readResourceAsFile(String resource) throws IOException {
        return new ClassPathResource(resource).getFile();
    }

    static InputStream readResourceAsStream(String resource) throws IOException {
        return new ClassPathResource(resource).getInputStream();
    }

    static Path resourcePath() {
        String resourcePath = Thread.class
            .getResource("/")
            .getPath();

        return Paths.get(resourcePath)
            .toAbsolutePath();
    }
}
