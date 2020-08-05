package com.baeldung.hexagonal.inbound;

import com.baeldung.hexagonal.model.Movie;
import com.baeldung.hexagonal.outbound.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieControllerImpl implements MovieController {

    @Autowired
    MovieService movieService;

    @Override
    public List<Movie> getMovies() {
        return movieService.getMovies();
    }

    @Override
    public ResponseEntity<Void> addMovies(@RequestBody Movie movie) {
        movieService.addMovie(movie);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
}
