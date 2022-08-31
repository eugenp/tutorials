package com.baeldung.produceimage.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;

@Controller
public class DataProducerController {

    @GetMapping("/get-text")
    public @ResponseBody String getText() {
        return "Hello world";
    }

    @GetMapping("/get-image")
    public @ResponseBody byte[] getImage() throws IOException {
        final InputStream in = getClass().getResourceAsStream("/com/baeldung/produceimage/image.jpg");
        return IOUtils.toByteArray(in);
    }

    @GetMapping(value = "/get-image-with-media-type", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImageWithMediaType() throws IOException {
        final InputStream in = getClass().getResourceAsStream("/com/baeldung/produceimage/image.jpg");
        return IOUtils.toByteArray(in);
    }

    @GetMapping("/get-image-dynamic-type")
    @ResponseBody
    public ResponseEntity<InputStreamResource> getImageDynamicType(@RequestParam("jpg") boolean jpg) {
        final MediaType contentType = jpg ? MediaType.IMAGE_JPEG : MediaType.IMAGE_PNG;
        final InputStream in = jpg ?
                getClass().getResourceAsStream("/com/baeldung/produceimage/image.jpg") :
                getClass().getResourceAsStream("/com/baeldung/produceimage/image.png");
        return ResponseEntity.ok()
                .contentType(contentType)
                .body(new InputStreamResource(in));
    }

    @GetMapping(value = "/get-file", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] getFile() throws IOException {
        final InputStream in = getClass().getResourceAsStream("/com/baeldung/produceimage/data.txt");
        return IOUtils.toByteArray(in);
    }

}
