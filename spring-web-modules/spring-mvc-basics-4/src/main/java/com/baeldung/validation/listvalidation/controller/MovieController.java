package com.baeldung.validation.listvalidation.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.validation.listvalidation.constraint.MaxSizeConstraint;
import com.baeldung.validation.listvalidation.model.Movie;
import com.baeldung.validation.listvalidation.service.MovieService;

@Validated
@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping
    public void addAll(@RequestBody @NotEmpty(message = "Input movie list cannot be empty.") @MaxSizeConstraint List<@Valid Movie> movies) {
        movieService.addAll(movies);
    }
}
