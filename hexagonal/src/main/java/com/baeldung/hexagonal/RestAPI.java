package com.baeldung.hexagonal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/translate")
public class RestAPI {
	private TranslationService translationService;

    @Autowired
    public RestAPI(TranslationService translationService) {
        this.translationService = translationService;
    }

    @PostMapping(value = "/{user}/{targetLang}")
    TranslationRequest translate(@PathVariable String user, @PathVariable String targetLang, @RequestParam String text) {
		return translationService.translate(user, targetLang, text);
    }

	@PostMapping(value = "/{user}/{targetLang}")
    TranslationRequest translate(@PathVariable String user, @PathVariable String targetLang, @RequestParam String text, @RequestParam boolean isFormal) {
		return translationService.translate(user, targetLang, text, isFormal);
    }
    
	
}