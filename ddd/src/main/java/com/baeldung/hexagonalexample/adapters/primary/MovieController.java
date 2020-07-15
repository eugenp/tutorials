package com.baeldung.hexagonalexample.adapters.primary;

import com.baeldung.hexagonalexample.entity.MovieEntity;
import com.baeldung.hexagonalexample.model.Movie;
import com.baeldung.hexagonalexample.ports.inbound.TopRatedMovieServicePort;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MovieController {
    @Autowired
    private TopRatedMovieServicePort movieServicePort;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/movies/{genre}")
    public ResponseEntity<List<Movie>> topRateMovies(@PathVariable String genre){
        List<MovieEntity> movies = movieServicePort.topRateMovies(genre);
        List<Movie> result = movies.stream().map(this::convertToDto).collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/movies")
    public ResponseEntity<Void> saveMovie(@RequestBody Movie movie){
        MovieEntity movieEntity = convertToEntity(movie);
        movieServicePort.addMovie(movieEntity);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    private Movie convertToDto(MovieEntity movieEntity){
        return modelMapper.map(movieEntity, Movie.class);
    }

    private MovieEntity convertToEntity(Movie movie){
        MovieEntity movieEntity = modelMapper.map(movie, MovieEntity.class);
        movieEntity.setGenre(movieEntity.getGenre().toLowerCase());
        return movieEntity;
    }



}
