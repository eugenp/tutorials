package com.baeldung.restassured.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Set;
import java.util.UUID;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.restassured.model.Movie;
import com.baeldung.restassured.service.AppService;

@RestController
public class AppController {

    @Autowired
    AppService appService;

    @GetMapping("/movies")
    public ResponseEntity<?> getMovies() {

        Set<Movie> result = appService.getAll();

        return ResponseEntity.ok()
            .body(result);
    }

    @PostMapping("/movie")
    @ResponseStatus(HttpStatus.CREATED)
    public Movie addMovie(@RequestBody Movie movie) {

        appService.add(movie);
        return movie;
    }

    @GetMapping("/movie/{id}")
    public ResponseEntity<?> getMovie(@PathVariable int id) {

        Movie movie = appService.findMovie(id);
        if (movie == null) {
            return ResponseEntity.badRequest()
                .body("Invalid movie id");
        }

        return ResponseEntity.ok(movie);
    }

    @GetMapping("/welcome")
    public ResponseEntity<?> welcome(HttpServletResponse response) {

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
        headers.add("sessionId", UUID.randomUUID()
            .toString());

        Cookie cookie = new Cookie("token", "some-token");
        cookie.setDomain("localhost");

        response.addCookie(cookie);

        return ResponseEntity.noContent()
            .headers(headers)
            .build();
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> getFile(@PathVariable int id) throws FileNotFoundException {

        File file = appService.getFile(id);

        if (file == null) {
            return ResponseEntity.notFound()
                .build();
        }

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
            .contentLength(file.length())
            .contentType(MediaType.parseMediaType("application/octet-stream"))
            .body(resource);
    }

}
