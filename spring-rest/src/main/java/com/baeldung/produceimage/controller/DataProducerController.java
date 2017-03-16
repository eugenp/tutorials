package com.baeldung.produceimage.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;

@Controller
public class DataProducerController {

    @GetMapping(value = "/get-image-without-mediatype")
    @ResponseBody public byte[] getImageWithoutMediaType() throws IOException {
        final InputStream in = getClass().getResourceAsStream("/com/baeldung/produceimage/image.jpg");
        return IOUtils.toByteArray(in);
    }

    @GetMapping(value = "/get-image", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody public byte[] getImage() throws IOException {
        final InputStream in = getClass().getResourceAsStream("/com/baeldung/produceimage/image.jpg");
        return IOUtils.toByteArray(in);
    }

    @GetMapping(value = "/get-file", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody public byte[] getFile() throws IOException {
        final InputStream in = getClass().getResourceAsStream("/com/baeldung/produceimage/data.txt");
        return IOUtils.toByteArray(in);
    }

}
