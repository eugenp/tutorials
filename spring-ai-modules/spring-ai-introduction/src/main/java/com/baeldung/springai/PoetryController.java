package com.baeldung.springai;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class PoetryController {

    private final PoetryService poetryService;

    PoetryController(PoetryService poetryService) {
        this.poetryService = poetryService;
    }

    @PostMapping("/poem/generate")
    ResponseEntity<Poem> generate(@RequestBody PoemGenerationRequest request) {
        Poem response = poetryService.generate(request.genre, request.theme);
        return ResponseEntity.ok(response);
    }

    record PoemGenerationRequest(String genre, String theme) {}

}