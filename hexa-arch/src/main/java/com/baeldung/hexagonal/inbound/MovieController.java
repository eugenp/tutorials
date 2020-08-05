package com.baeldung.hexagonal.inbound;

import com.baeldung.hexagonal.model.Movie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface MovieController {
    @GetMapping
    List<Movie> getMovies();

    @PostMapping
    ResponseEntity<Void> addMovies(@RequestBody Movie movie);
}
