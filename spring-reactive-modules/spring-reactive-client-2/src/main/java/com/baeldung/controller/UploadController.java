package com.baeldung.controller;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baeldung.service.ReactiveUploadService;

import reactor.core.publisher.Mono;

@RestController
public class UploadController {
    final ReactiveUploadService uploadService;

    public UploadController(ReactiveUploadService uploadService) {
        this.uploadService = uploadService;
    }

    @PostMapping(path = "/upload")
    @ResponseBody
    public Mono<HttpStatusCode> uploadPdf(@RequestParam("file") final MultipartFile multipartFile) {
        return uploadService.uploadPdf(multipartFile.getResource());
    }

    @PostMapping(path = "/upload/multipart")
    @ResponseBody
    public Mono<HttpStatusCode> uploadMultipart(@RequestParam("file") final MultipartFile multipartFile) {
        return uploadService.uploadMultipart(multipartFile);
    }
}
