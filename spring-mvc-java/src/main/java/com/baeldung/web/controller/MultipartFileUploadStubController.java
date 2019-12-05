package com.baeldung.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MultipartFileUploadStubController {
    
    private static final Logger logger = LoggerFactory.getLogger(MultipartFileUploadStubController.class);

    @PostMapping("/stub/multipart")
    @ResponseStatus(HttpStatus.OK)
    public void uploadFile(MultipartFile file, String text, String text1, String text2, MultipartFile upstream) {
        logger.info("Uploaded file: " + format(file));
        logger.info("  - text: [" + format(text) + "]");
        logger.info("  - text1: [" + format(text1) + "]");
        logger.info("  - text2: [" + format(text2) + "]");
        logger.info("  - upstream: [" + format(upstream) + "]");
    }
    
    private static String format(MultipartFile file) {
        return file == null ? "<null>" : file.getOriginalFilename() + " (size: " + file.getSize() + " bytes)";
    }
    
    private static String format(String str) {
        return str == null ? "<null>" : str;
    }
}
