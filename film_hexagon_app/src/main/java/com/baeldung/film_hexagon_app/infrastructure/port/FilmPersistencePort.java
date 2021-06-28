package com.baeldung.film_hexagon_app.infrastructure.port;

import com.baeldung.film_hexagon_app.domain.Film;

import java.util.List;
import java.util.Optional;


public interface FilmPersistencePort {

    List<Film> getAllFilms();

    Optional<Film> getFilmById(Long FilmId);

    Film addFilm(Film Film);

    void removeFilm(Long FilmId);
}
