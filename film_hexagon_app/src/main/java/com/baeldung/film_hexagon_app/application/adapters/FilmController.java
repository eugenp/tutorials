package com.baeldung.film_hexagon_app.application.adapters;

import com.baeldung.film_hexagon_app.application.port.FilmServicePort;
import com.baeldung.film_hexagon_app.domain.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {
    @Autowired
    private FilmServicePort FilmService;

    @GetMapping
    public ResponseEntity<List<Film>> getFilms() {
        return new ResponseEntity<List<Film>>(FilmService.getFilms(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Film> addFilm(@RequestBody Film Film) {
        return new ResponseEntity<Film>(FilmService.addFilm(Film), HttpStatus.CREATED);
    }
}
