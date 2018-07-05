package com.baeldung.web.upload.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/fileserver")
public class FileServerResource {

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> processFile(@RequestParam("file") MultipartFile multipartFile) throws IOException {

        byte[] bytes = multipartFile.getBytes();

        System.out.println("File Name: " + multipartFile.getOriginalFilename());
        System.out.println("File Content Type: " + multipartFile.getContentType());
        System.out.println("File Content:\n" + new String(bytes));

        return (new ResponseEntity<>("Successful", null, HttpStatus.OK));
    }
}