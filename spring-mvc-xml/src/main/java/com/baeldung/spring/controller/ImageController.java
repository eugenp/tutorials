package com.baeldung.spring.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.ServletContextResourceLoader;

@Controller
public class ImageController {

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "/image-response-entity", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getImageAsREsponseEntity() throws IOException {
        final InputStream in = servletContext.getResourceAsStream("/WEB-INF/images/image-example.jpg");
        byte[] media = IOUtils.toByteArray(in);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<byte[]>(media, headers, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/image-byte-array", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImageAsByteArray() throws IOException {
        final InputStream in = servletContext.getResourceAsStream("/WEB-INF/images/image-example.jpg");
        return IOUtils.toByteArray(in);
    }
    
    

    @ResponseBody
    @RequestMapping(value = "/image-resource",  method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public Resource getImageAsResource() {
       ResourceLoader resourceLoader = new ServletContextResourceLoader(servletContext);
       return resourceLoader.getResource("/WEB-INF/images/image-example.jpg");
    }
}
