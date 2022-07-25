package com.baeldung.cloud.openfeign.fileupload.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baeldung.cloud.openfeign.exception.NotFoundException;

@RestController
public class FileController {

    @PostMapping(value = "/upload-error")
    public String uploadFileWithFallback(@RequestPart(value = "file") MultipartFile file) throws NotFoundException {
        throw new NotFoundException("Not Found!!!");
    }

}