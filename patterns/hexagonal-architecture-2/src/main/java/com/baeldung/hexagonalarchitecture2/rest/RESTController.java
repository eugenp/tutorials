package com.baeldung.hexagonalarchitecture2.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.baeldung.hexagonalarchitecture2.core.RockPaperScissorsService;
import com.baeldung.hexagonalarchitecture2.core.domain.Game;
import com.baeldung.hexagonalarchitecture2.core.domain.Move;
import com.baeldung.hexagonalarchitecture2.core.domain.Result;
import com.baeldung.hexagonalarchitecture2.core.ports.GameRepository.GameNotFoundException;

@RestController
@RequestMapping("/rest")
public class RESTController {

    private final RockPaperScissorsService rockPaperScissorsService;

    public RESTController(RockPaperScissorsService service) {
        this.rockPaperScissorsService = service;
    }

    @PostMapping(value = "/game/new", produces = "application/json")
    public Game startNewGame() {
        try {
            return rockPaperScissorsService.startNewGame();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to start new game");
        }
    }

    @PostMapping(value = "/game/{id}", produces = "application/json")
    public Result playGame(@PathVariable int id, @RequestParam Move move) {
        try {
            return rockPaperScissorsService.playGame(id, move);
        } catch (GameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Game with given ID not found");
        }
    }
}
