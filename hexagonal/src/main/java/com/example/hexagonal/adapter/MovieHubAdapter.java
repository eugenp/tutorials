package com.example.hexagonal.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hexagonal.domain.Movie;
import com.example.hexagonal.port.MovieHubService;

@RestController
public class MovieHubAdapter {

    @Autowired
    MovieHubService movieService;

    @GetMapping("/getmovie")
    public Movie getMovieTitleNumber(@RequestParam(value = "title") String name) {
        return movieService.getMovieDetails(name);
    }

    @PostMapping("/adduser")
    public String registerNewUser(@RequestParam(value = "name") String userName, @RequestParam(value = "location") String userLocation) {
        return movieService.registerNewUser(userName, userLocation);
    }

    @PostMapping("/addmovie")
    public String addMovie(@RequestParam(value = "id") int movieId, @RequestParam(value = "name") String movieName, @RequestParam(value = "year") int releaseYear, @RequestParam(value = "imdb") double imdbScore, @RequestParam(value = "rt") int rtScore) {
        return movieService.addMovie(movieId, movieName, releaseYear, imdbScore, rtScore);
    }
}
