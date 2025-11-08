package com.baeldung.postman.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.baeldung.postman.model.JsonRequest;

@Controller
public class PostmanUploadController {

    @PostMapping("/uploadFile")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok()
            .body("file received successfully");
    }

    @PostMapping("/uploadJson")
    public ResponseEntity<String> handleJsonInput(@RequestBody JsonRequest json) {
        return ResponseEntity.ok()
            .body(json.getId() + json.getName());
    }

    @PostMapping("/uploadJsonAndMultipartData")
    public ResponseEntity<String> handleJsonAndMultipartInput(@RequestPart("data") JsonRequest json, @RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok()
            .body(json.getId() + json.getName());
    }

    @PostMapping(value = "/uploadSingleFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> handleSingleFileUpload(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok("file received successfully");
    }

    @PostMapping(value = "/uploadJsonAndMultipartData", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> handleUploadeJsonAndMultipartInput(@RequestPart("data") JsonRequest json, @RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok(json.getId() + json.getName());
    }
}
