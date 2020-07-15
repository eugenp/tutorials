package com.baeldung.hexagonalexample.adapters.secondary;

import com.baeldung.hexagonalexample.entity.MovieEntity;
import com.baeldung.hexagonalexample.ports.outbound.MoviePersistencePort;
import com.baeldung.hexagonalexample.repository.MovieJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MovieJpaAdapter implements MoviePersistencePort {

    @Autowired
    private MovieJpaRepository movieJpaRepository;

    @Override
    public List<MovieEntity> findByGenre(String genre) {
        List<MovieEntity> movies = movieJpaRepository.findAll();
        return movies.stream().filter(movieEntity -> movieEntity.getGenre().equalsIgnoreCase(genre)).collect(Collectors.toList());
    }

    @Override
    public void save(MovieEntity movie) {
        movieJpaRepository.save(movie);
    }
}
