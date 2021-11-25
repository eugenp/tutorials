package com.baeldung.adapters.web;

import com.baeldung.application.port.input.AddRankLevelUseCase;
import com.baeldung.application.port.input.ReduceRankLevelUseCase;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class GamerAccountController {

    private final AddRankLevelUseCase addRankLevelUseCase;
    private final ReduceRankLevelUseCase reduceRankLevelUseCase;

    public GamerAccountController(AddRankLevelUseCase addRankLevelUseCase, ReduceRankLevelUseCase reduceRankLevelUseCase) {
        this.addRankLevelUseCase = addRankLevelUseCase;
        this.reduceRankLevelUseCase = reduceRankLevelUseCase;
    }

    @PostMapping(value = "/{id}/addRankLevel/{level}")
    void addRankLevel(@PathVariable final Long id, @PathVariable final int level) {
        addRankLevelUseCase.AddRankLevel(id, level);
    }

    @PostMapping(value = "/{id}/reduceRankLevel/{level}")
    void reduceRankLevel(@PathVariable final Long id, @PathVariable final int level) {
        reduceRankLevelUseCase.reduceRankLevel(id, level);
    }
}
