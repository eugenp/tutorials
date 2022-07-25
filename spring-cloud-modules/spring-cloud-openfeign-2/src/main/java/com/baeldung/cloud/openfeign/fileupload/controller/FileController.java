package com.baeldung.cloud.openfeign.fileupload.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baeldung.cloud.openfeign.fileupload.service.UploadService;

@RestController
public class FileController {

    @Autowired
    private UploadService service;

    @PostMapping(value = "/upload-mannual-client")
    public boolean handleFileUploadWithManualClient(@RequestPart(value = "file") MultipartFile file) {
        return service.uploadFileWithManualClient(file);
    }

    @PostMapping(value = "/upload-file")
    public boolean handleFileUpload(@RequestPart(value = "file") MultipartFile file) {
        return service.uploadFileWithManualClient(file);
    }

    @PostMapping(value = "/upload-with-fallbackfactory")
    public String uploadFileWithFallbackFactory(@RequestPart(value = "file") MultipartFile file) {
        return service.uploadFileWithFallbackFactory(file);
    }

    @PostMapping(value = "/upload-with-fallback")
    public String uploadFileWithFallback(@RequestPart(value = "file") MultipartFile file) {
        return service.uploadFileWithFallback(file);
    }

}