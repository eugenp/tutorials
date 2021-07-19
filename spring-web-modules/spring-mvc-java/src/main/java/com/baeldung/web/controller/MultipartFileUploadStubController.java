package com.baeldung.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MultipartFileUploadStubController {

    @PostMapping("/stub/multipart")
    public ResponseEntity<UploadResultResource> uploadFile(MultipartFile file, String text, String text1, String text2, MultipartFile upstream) {
        UploadResultResource result = new UploadResultResource(file, text, text1, text2, upstream);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    public static class UploadResultResource {
        
        private final String file;
        private final String text;
        private final String text1;
        private final String text2;
        private final String upstream;
        
        public UploadResultResource(MultipartFile file, String text, String text1, String text2, MultipartFile upstream) {
            this.file = format(file);
            this.text = text;
            this.text1 = text1;
            this.text2 = text2;
            this.upstream = format(upstream);
        }
        
        private static String format(MultipartFile file) {
            return file == null ? null : file.getOriginalFilename() + " (size: " + file.getSize() + " bytes)";
        }

        public String getFile() {
            return file;
        }

        public String getText() {
            return text;
        }

        public String getText1() {
            return text1;
        }

        public String getText2() {
            return text2;
        }

        public String getUpstream() {
            return upstream;
        }
    }
}
