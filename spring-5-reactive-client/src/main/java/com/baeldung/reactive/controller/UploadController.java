package com.baeldung.reactive.controller;


import com.baeldung.reactive.service.ReactiveUploadService;
import org.springframework.http.HttpStatus;
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
    public Mono<HttpStatus> uploadPdf(@RequestParam("file") final MultipartFile multipartFile) {
        return uploadService.uploadPdf(multipartFile.getResource());
    }

    @PostMapping(path = "/upload/multipart")
    @ResponseBody
    public Mono<HttpStatus> uploadMultipart(@RequestParam("file") final MultipartFile multipartFile) {
        return uploadService.uploadMultipart(multipartFile);
    }


    /**
     * Fake upload endpoint returning "OK" HttpStatus
     * @return "OK" HttpStatus
     */
    @PostMapping(path = "/external/upload")
    @ResponseBody
    public HttpStatus externalUpload() {
        return HttpStatus.OK;
    }

    @GetMapping("/trixi")
    public String returnTrixi() {
        return "Trixi";

    }
}
