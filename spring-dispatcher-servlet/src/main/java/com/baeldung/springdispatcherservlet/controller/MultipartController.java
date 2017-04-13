package com.baeldung.springdispatcherservlet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;

@Controller
@RequestMapping(value = "/upload", method = RequestMethod.POST)
public class MultipartController {

    @Autowired
    ServletContext context;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void FileuploadController(@RequestParam("file") MultipartFile file) {
        try {
            String path = context.getRealPath("/WEB-INF/uploaded") + File.separator + file.getOriginalFilename();
            File destinationFile = new File(path);
            file.transferTo(destinationFile);
        } catch (Exception e) {
            System.out.println("Exception uploading multipart: " + e);
        }
    }
}
