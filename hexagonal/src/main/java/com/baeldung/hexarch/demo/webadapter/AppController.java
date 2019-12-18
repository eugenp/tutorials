package com.baeldung.hexarch.demo.webadapter;

import com.baeldung.hexarch.demo.core.Film;
import com.baeldung.hexarch.demo.core.FilmApp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequiredArgsConstructor
@RestController
class AppController {

    private final FilmApp coreApp;

    @PostMapping("/film")
    public Film addFilm(@RequestBody Film film) {
        return coreApp.addFilm(film);
    }

    @GetMapping("/film")
    public List<Film> listFilms() {
        return coreApp.getFilmsOrderedByScore();
    }

}
