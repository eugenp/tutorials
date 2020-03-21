package com.baeldung.architecture.hexagonal.adapter;

import com.baeldung.architecture.hexagonal.Movie;
import com.baeldung.architecture.hexagonal.port.MovieDataSource;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MovieFileDataSourceAdapter implements MovieDataSource {
    private ObjectMapper mapper;
    public MovieFileDataSourceAdapter() {
        this.mapper = new ObjectMapper();
    }

    public List<Movie> allMovies() throws IOException  {
        InputStream moviesStream = this.getClass().getClassLoader().getResourceAsStream("movies.json");
        return mapper.readValue(moviesStream, new TypeReference<List<Movie>>() {});
    }
}
