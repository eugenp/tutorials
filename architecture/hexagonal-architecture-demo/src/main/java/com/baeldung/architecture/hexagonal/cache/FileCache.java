package com.baeldung.architecture.hexagonal.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

public class FileCache implements Cache {
    private static final Logger logger = LoggerFactory.getLogger(FileCache.class);

    private static final String baseCacheLocation = "/tmp/cache";
    private static final String cacheLocationFormat = "/tmp/cache/%s.cache";

    public FileCache() {
        try {
            Files.createDirectory(Paths.get(baseCacheLocation));
        } catch (FileAlreadyExistsException e) {
            logger.info("Cache location {} already exists.", baseCacheLocation);
        } catch (IOException e) {
            logger.warn("Unable to create base cache location. FileCache won't be available. Error Message: {}", e.getMessage());
        }
    }

    @Override
    public void put(String key, String value) {
        Path cacheLocation = Paths.get(String.format(cacheLocationFormat, key));

        try {
            Files.writeString(cacheLocation, value, StandardCharsets.UTF_16, StandardOpenOption.CREATE_NEW);
        } catch (IOException e) {
            logger.warn("Unable to write to cache. Error Message: {}", e.getMessage());
        }
    }

    @Override
    public Optional<String> get(String key) {
        Path cacheLocation = Paths.get(String.format(cacheLocationFormat, key));

        try {
            return Optional.of(Files.readString(cacheLocation, StandardCharsets.UTF_16));
        } catch (IOException e) {
            logger.warn("Unable to read from cache. Error Message: {}", e.getMessage());
            return Optional.empty();
        }
    }
}
