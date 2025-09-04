package com.baeldung.streaming;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/mvc/files")
public class MvcStreamingController {

    private static final Path UPLOAD_DIR = Path.of("mvc-uploads");

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFileStreaming(@RequestPart("filePart") MultipartFile filePart) throws IOException {
        Path targetPath = UPLOAD_DIR.resolve(filePart.getOriginalFilename());
        Files.createDirectories(targetPath.getParent());
        try (InputStream inputStream = filePart.getInputStream(); OutputStream outputStream = Files.newOutputStream(targetPath)) {
            inputStream.transferTo(outputStream);
        }
        return ResponseEntity.ok("Upload successful: " + filePart.getOriginalFilename());
    }

    @GetMapping("/download")
    public StreamingResponseBody downloadFiles(HttpServletResponse response) throws IOException {
        String boundary = "filesBoundary";
        response.setContentType("multipart/mixed; boundary=" + boundary);
        List<Path> files = List.of(UPLOAD_DIR.resolve("file1.txt"), UPLOAD_DIR.resolve("file2.txt"));
        return outputStream -> {
            try (BufferedOutputStream bos = new BufferedOutputStream(outputStream); OutputStreamWriter writer = new OutputStreamWriter(bos)) {
                for (Path file : files) {
                    writer.write("--" + boundary + "\r\n");
                    writer.write("Content-Type: application/octet-stream\r\n");
                    writer.write("Content-Disposition: attachment; filename=\"" + file.getFileName() + "\"\r\n\r\n");
                    writer.flush();
                    Files.copy(file, bos);
                    bos.write("\r\n".getBytes());
                    bos.flush();
                }
                writer.write("--" + boundary + "--\r\n");
                writer.flush();
            }
        };
    }
}