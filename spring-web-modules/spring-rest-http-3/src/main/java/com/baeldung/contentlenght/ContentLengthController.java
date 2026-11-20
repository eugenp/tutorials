package com.baeldung.contentlenght;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class ContentLengthController {

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        String body = "Hello Spring MVC!";
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);

        return ResponseEntity.ok()
          .contentLength(bytes.length)
          .body(body);
    }

    @GetMapping("/binary")
    public ResponseEntity<byte[]> binary() {
        byte[] data = {1, 2, 3, 4, 5};

        return ResponseEntity.ok()
          .contentLength(data.length)
          .contentType(MediaType.APPLICATION_OCTET_STREAM)
          .body(data);
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> download() throws IOException {
        Path filePath = Paths.get("example.pdf"); // For tests, this file should exist
        Resource resource = new UrlResource(filePath.toUri());

        long fileSize = Files.size(filePath);

        return ResponseEntity.ok()
          .contentLength(fileSize)
          .contentType(MediaType.APPLICATION_PDF)
          .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"example.pdf\"")
          .body(resource);
    }

    @GetMapping("/manual")
    public ResponseEntity<String> manual() {
        String body = "Manual Content-Length";
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(bytes.length);

        return ResponseEntity.ok()
          .headers(headers)
          .body(body);
    }
}
