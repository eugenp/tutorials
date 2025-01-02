package com.baeldung.graphql.fileupload;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Component
public class FileUploadDataFetcher implements DataFetcher<String> {
    private final FileStorageService fileStorageService;

    public FileUploadDataFetcher(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @Override
    public String get(DataFetchingEnvironment environment) {
        MultipartFile file = environment.getArgument("file");
        String description = environment.getArgument("description");
        String storedFilePath = fileStorageService.store(file, description);
        return String.format("File stored at: %s, Description: %s", storedFilePath, description);
    }
}