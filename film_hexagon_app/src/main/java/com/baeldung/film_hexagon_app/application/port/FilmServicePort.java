package com.baeldung.film_hexagon_app.application.port;

import com.baeldung.film_hexagon_app.domain.Film;

import java.util.List;
import java.util.Optional;


public interface FilmServicePort {
    
    List<Film> getFilms();

    Optional<Film> getFilmById(Long FilmId);

    Film addFilm(Film Film);

    void removeFilm(Long FilmId);

}
