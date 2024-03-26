package com.baeldung.reactive.controller;


import com.baeldung.reactive.service.ReactiveUploadService;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
