package com.baeldung.film_hexagon_app.infrastructure.adapters;


import com.baeldung.film_hexagon_app.domain.Film;
import com.baeldung.film_hexagon_app.infrastructure.port.FilmPersistencePort;
import com.baeldung.film_hexagon_app.infrastructure.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FilmJpaAdapter implements FilmPersistencePort {

    @Autowired
    private FilmRepository filmRepository;

    @Override
    public List<Film> getAllFilms() {
        return (List<Film>) filmRepository.findAll();
    }

    @Override
    public Film addFilm(Film film) {
        return filmRepository.save(film);
    }

    @Override
    public void removeFilm(Long filmId) {
        filmRepository.deleteById(filmId);
    }

    @Override
    public Optional<Film> getFilmById(Long filmId) {
        return filmRepository.findById(filmId);
    }
}