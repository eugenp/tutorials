package com.baeldung.streamlargefile.server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/large-file")
public class LargeFileController {

    public static final Path downloadPath = Paths.get("/tmp/large.dat");

    @GetMapping("size")
    Long getSize() throws IOException {
        return Files.size(downloadPath);
    }

    @GetMapping
    ResponseEntity<Resource> get() {
        return ResponseEntity.ok()
            .body(new FileSystemResource(downloadPath));
    }
}
