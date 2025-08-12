package com.baeldung.springai.web;

import com.baeldung.springai.dto.PoetryDto;
import com.baeldung.springai.service.PoetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ai")
public class PoetryController {

    private final PoetryService poetryService;

    @Autowired
    public PoetryController(PoetryService poetryService) {
        this.poetryService = poetryService;
    }

    @GetMapping("/cathaiku")
    public ResponseEntity<String> generateHaiku() {
        return ResponseEntity.ok(poetryService.getCatHaiku());
    }

    @GetMapping("/poetry")
    public ResponseEntity<PoetryDto> generatePoetry(@RequestParam("genre") String genre, @RequestParam("theme") String theme) {
        return ResponseEntity.ok(poetryService.getPoetryByGenreAndTheme(genre, theme));
    }
}
