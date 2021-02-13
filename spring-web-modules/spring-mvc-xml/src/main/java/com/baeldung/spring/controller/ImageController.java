package com.baeldung.spring.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.ServletContextResource;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController {

    @Autowired
    private ServletContext servletContext;

    @RequestMapping(value = "/image-view", method = RequestMethod.GET)
    public String imageView() throws IOException {
        return "image-download";
    }

    @RequestMapping(value = "/image-manual-response", method = RequestMethod.GET)
    public void getImageAsByteArray(HttpServletResponse response) throws IOException {
        final InputStream in = servletContext.getResourceAsStream("/WEB-INF/images/image-example.jpg");
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        IOUtils.copy(in, response.getOutputStream());
    }

    @RequestMapping(value = "/image-byte-array", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getImageAsByteArray() throws IOException {
        final InputStream in = servletContext.getResourceAsStream("/WEB-INF/images/image-example.jpg");
        return IOUtils.toByteArray(in);
    }

    @RequestMapping(value = "/image-response-entity", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getImageAsResponseEntity() throws IOException {
        ResponseEntity<byte[]> responseEntity;
        final HttpHeaders headers = new HttpHeaders();
        final InputStream in = servletContext.getResourceAsStream("/WEB-INF/images/image-example.jpg");
        byte[] media = IOUtils.toByteArray(in);
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        responseEntity = new ResponseEntity<>(media, headers, HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping(value = "/image-resource", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Resource> getImageAsResource() {
        final HttpHeaders headers = new HttpHeaders();
        Resource resource = new ServletContextResource(servletContext, "/WEB-INF/images/image-example.jpg");
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}
