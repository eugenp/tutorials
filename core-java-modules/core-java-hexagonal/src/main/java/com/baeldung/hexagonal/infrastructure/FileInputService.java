package com.baeldung.hexagonal.infrastructure;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.baeldung.hexagonal.domain.InputService;

public class FileInputService implements InputService {

    private String fileName;

    public FileInputService(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String readText() {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(fileName));
            return new String(encoded, StandardCharsets.US_ASCII);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
