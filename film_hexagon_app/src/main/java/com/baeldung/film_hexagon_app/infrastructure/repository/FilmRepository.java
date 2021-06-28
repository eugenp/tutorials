package com.baeldung.film_hexagon_app.infrastructure.repository;

import com.baeldung.film_hexagon_app.domain.Film;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FilmRepository extends CrudRepository<Film, Long>
{
    List<Film> getAllFilms(String lastName);

    Film addFilm(Film film);

}
