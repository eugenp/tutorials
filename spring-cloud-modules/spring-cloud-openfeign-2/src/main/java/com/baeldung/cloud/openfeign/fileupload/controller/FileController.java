package com.baeldung.cloud.openfeign.fileupload.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baeldung.cloud.openfeign.exception.NotFoundException;
import com.baeldung.cloud.openfeign.fileupload.service.UploadService;

@RestController
public class FileController {
    
    @Autowired
    private UploadService service;
    
    @PostMapping(value = "/upload")
    public String handleFileUpload(@RequestPart(value = "file") MultipartFile file) {
        return service.uploadFile(file);
    }
    
    @PostMapping(value = "/upload-mannual-client")
    public boolean handleFileUploadWithManualClient(
            @RequestPart(value = "file") MultipartFile file) {
        return service.uploadFileWithManualClient(file);
    }
    
    @PostMapping(value = "/upload-error")
    public String handleFileUploadError(@RequestPart(value = "file") MultipartFile file) throws NotFoundException {
        return service.uploadFileWithCause(file);
    }
    
}