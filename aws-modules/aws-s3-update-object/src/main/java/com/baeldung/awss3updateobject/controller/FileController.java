package com.baeldung.awss3updateobject.controller;

import com.baeldung.awss3updateobject.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/file")
public class FileController {

    @Autowired
    FileService fileService;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        return this.fileService.uploadFile(multipartFile);
    }

    @PostMapping("/update")
    public String updateFile(@RequestParam("file") MultipartFile multipartFile, @RequestParam("filePath")  String exitingFilePath) throws Exception {
        return this.fileService.updateFile(multipartFile, exitingFilePath);
    }
}
