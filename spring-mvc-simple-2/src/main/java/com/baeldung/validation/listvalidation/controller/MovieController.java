package com.baeldung.validation.listvalidation.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.baeldung.validation.listvalidation.model.Movie;
import com.baeldung.validation.listvalidation.repository.MovieRepository;

@Validated
@RestController
@RequestMapping("/movie")
public class MovieController {

    private final Logger LOGGER = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    private MovieRepository movieRepository;

    @RequestMapping(method = RequestMethod.POST)
    public void add(@RequestBody @Valid Movie movie, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder builder = new StringBuilder();
            bindingResult.getAllErrors()
                .forEach(error -> builder.append(" " + error.getDefaultMessage()));
            LOGGER.error(builder.toString());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.toString());
        }
        movieRepository.add(movie);
    }

    @RequestMapping(value = "/batch", method = RequestMethod.POST)
    public void addAll(@RequestBody List<@Valid Movie> movie) {
        movieRepository.addAll(movie);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{name}")
    public Movie get(@PathVariable String name) throws Exception {
        return movieRepository.get(name);
    }
}
