package com.baeldung.hexarch.demo.repoadapter;

import com.baeldung.hexarch.demo.core.Film;
import com.baeldung.hexarch.demo.core.FilmRepo;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
class InMemoryRepo implements FilmRepo {

    Map<Integer, Film> memDb = new HashMap<>();

    @Override
    public Film insertFilm(Film film) {
        return memDb.put(film.getId(), film);
    }

    @Override
    public Film getFilm(Integer id) {
        return memDb.get(id);
    }

    @Override
    public Collection<Film> listFilms() {
        return memDb.values();
    }
}
