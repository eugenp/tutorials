package com.baeldung.hexarch.demo.core;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class FilmAppImpl implements FilmApp {

    private final FilmRepo repo;

    @Override
    public Film addFilm(Film film) {
        Film existing = repo.getFilm(film.getId());
        if (existing == null) {
            return repo.insertFilm(film);
        }
        return existing;
    }

    @Override
    public List<Film> getFilmsOrderedByScore() {
        return repo.listFilms().stream()
                .sorted(Comparator.comparingInt(Film::getScore).reversed())
                .collect(Collectors.toList());
    }
}
