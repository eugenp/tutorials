package com.baeldung.spring.ai.web;

import com.baeldung.spring.ai.dto.PoetryDto;
import com.baeldung.spring.ai.service.PoetryService;
import org.springframework.ai.client.AiClient;
import org.springframework.ai.client.AiResponse;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.ai.prompt.PromptTemplate;
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
    public ResponseEntity<String> generateHaiku(){
        return ResponseEntity.ok(poetryService.getCatHaiku());
    }

    @GetMapping("/poetry")
    public ResponseEntity<PoetryDto> generatePoetry(
            @RequestParam("genre") String genre,
            @RequestParam("theme") String theme
    ){
        return ResponseEntity.ok(poetryService.getPoetryByGenreAndTheme(genre, theme));
    }
}
