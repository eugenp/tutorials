package com.baeldung.aws.rest.s3.download.web;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;

import com.baeldung.aws.rest.s3.download.dto.FileDownloadResponse;
import com.baeldung.aws.rest.s3.download.service.FileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping(value = "/download/{encodedUrl}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> downloadFile(@PathVariable String encodedUrl) throws IOException {
        String s3Url = UriUtils.decode(encodedUrl, StandardCharsets.UTF_8);
        FileDownloadResponse fileDownloadResponse = fileService.downloadFile(s3Url);

        if (fileDownloadResponse != null) {
            try {
                byte[] fileContent = fileDownloadResponse.getFileContent();
                String originalFilename = fileDownloadResponse.getOriginalFilename();
                String contentType = fileDownloadResponse.getContentType();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.parseMediaType(contentType));
                headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + originalFilename);

                return ResponseEntity.ok()
                  .headers(headers)
                  .body(fileContent);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                  .body(null);
            }
        } else {
            return ResponseEntity.notFound()
              .build();
        }
    }
}