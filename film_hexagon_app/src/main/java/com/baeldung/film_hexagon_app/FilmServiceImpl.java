package com.baeldung.film_hexagon_app;


import com.baeldung.film_hexagon_app.application.port.FilmServicePort;
import com.baeldung.film_hexagon_app.domain.Film;
import com.baeldung.film_hexagon_app.infrastructure.port.FilmPersistencePort;

import java.util.List;
import java.util.Optional;


class FilmServiceImpl implements FilmServicePort {

    private final FilmPersistencePort filmRepository;

    FilmServiceImpl(FilmPersistencePort FilmRepository) {
        this.filmRepository = FilmRepository;
    }

    @Override
    public List<Film> getFilms() {
        return filmRepository.getAllFilms();
    }

    @Override
    public Optional<Film> getFilmById(Long FilmId) {
        return filmRepository.getFilmById(FilmId);
    }

    @Override
    public Film addFilm(Film Film) {
        return filmRepository.addFilm(Film);
    }

    @Override
    public void removeFilm(Long FilmId) {
        filmRepository.removeFilm(FilmId);
    }


}
