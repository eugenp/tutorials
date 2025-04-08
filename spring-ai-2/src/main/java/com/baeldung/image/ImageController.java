package com.baeldung.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private CarCountService carCountService;

    @PostMapping("/car-count")
    public ResponseEntity<?> getCarCounts(@RequestParam("colors") String colors, @RequestParam("file") MultipartFile file) {

        try (InputStream inputStream = file.getInputStream()) {
            var carCount = carCountService.getCarCount(inputStream, file.getContentType(), colors);
            return ResponseEntity.ok(carCount);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error uploading image");
        }
    }

}
