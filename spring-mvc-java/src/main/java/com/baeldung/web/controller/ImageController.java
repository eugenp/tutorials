package com.baeldung.web.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ImageController {

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "/image-byte-array", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImageAsByteArray() throws IOException {
        final InputStream in = servletContext.getResourceAsStream("/WEB-INF/images/image-example.jpg");
        return IOUtils.toByteArray(in);
    }
}
