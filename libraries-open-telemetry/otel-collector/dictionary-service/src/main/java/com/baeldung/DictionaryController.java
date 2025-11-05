package com.baeldung;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.context.Scope;
import io.opentelemetry.context.Context;

@RestController
@RequestMapping("/api/words")
public class DictionaryController {
    private static final Logger logger = LoggerFactory.getLogger(DictionaryController.class);
    private final DictionaryService dictionaryService;

    @Autowired
    OpenTelemetry openTelemetry;

    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @GetMapping("/random")
    public Map<String, String> getRandomWord() {
        Map<String, String> response = new HashMap<>();
        Tracer tracer = openTelemetry.getTracer("dictionary-service-application");
        Span span = tracer.spanBuilder("get_word")
            .setParent(Context.current().with(Span.current()))
            .startSpan();
        try (Scope scope = span.makeCurrent()) {
            logger.info("Processing received request for a random word");
            String word = dictionaryService.getRandomWord();
            String meaning = dictionaryService.getWordMeaning(word);

            logger.info("Generated result: {} , {}", word, meaning);
            response.put("word", word);
            response.put("meaning", meaning);
        } catch(Exception exception ) {
            span.recordException(exception);
        } finally {
            span.end();
        }

        return response;
    }
}