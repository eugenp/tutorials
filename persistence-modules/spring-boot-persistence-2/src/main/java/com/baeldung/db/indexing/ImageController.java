package com.baeldung.db.indexing;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
class ImageController {

    @Autowired
    ImageDbRepository imageDbRepository;

    @PostMapping("/image")
    long uploadImage(@RequestParam MultipartFile multipartImage) throws IOException {
        Image dbImage = new Image();
        dbImage.setName(multipartImage.getOriginalFilename());
        dbImage.setContent(multipartImage.getBytes());

        return imageDbRepository.save(dbImage)
            .getId();
    }

    @GetMapping(value = "/image/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
    Resource downloadImage(@PathVariable Long imageId) {
        byte[] image = imageDbRepository.findById(imageId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
            .getContent();

        return new ByteArrayResource(image);
    }

}
