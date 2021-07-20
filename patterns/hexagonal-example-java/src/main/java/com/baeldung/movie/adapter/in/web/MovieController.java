package com.baeldung.movie.adapter.in.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.movie.application.port.in.MovieServicePort;
import com.baeldung.movie.domain.MovieDto;

@RestController
@RequestMapping("/movie")
public class MovieController {
    
    @Autowired
    private MovieServicePort movieServicePort;

    @PostMapping("/add")
    public MovieDto addMovie(@RequestBody MovieDto movieDto) {
        return movieServicePort.addMovie(movieDto);
    }

    @PutMapping("/update")
    public MovieDto updateMovie(@RequestBody MovieDto movieDto) {
        return movieServicePort.updateMovie(movieDto);
    }

    @GetMapping("/get/{id}")
    public MovieDto getMovieByID(@PathVariable long id) {
        return movieServicePort.getMovieById(id);
    }

    @GetMapping("/get")
    public List<MovieDto> getAllMovie() {
        return movieServicePort.getMovies();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteMovieByID(@PathVariable long id) {
        movieServicePort.deleteMovieById(id);
    }

}
