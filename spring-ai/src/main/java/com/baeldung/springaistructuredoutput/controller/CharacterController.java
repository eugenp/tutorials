package com.baeldung.springaistructuredoutput.controller;

import com.baeldung.springaistructuredoutput.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/character")
public class CharacterController {

    private final CharacterService characterService;

    @Autowired
    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    // Single character object

    @GetMapping("/chat-model/{race}")
    public Character generateCharacterChatModel(@PathVariable String race) {
        return characterService.generateCharacterChatModel(race);
    }

    @GetMapping("/chat-client/{race}")
    public Character generateCharacterChatClient(@PathVariable String race) {
        return characterService.generateCharacterChatClient(race);
    }

    // List of character objects

    @GetMapping("/chat-model/list/{amount}")
    public List<Character> generateListOfCharactersChatModel(@PathVariable int amount) {
        if (amount > 10) {
            throw new IllegalArgumentException("Amount must be less than 10");
        }
        return characterService.generateListOfCharactersChatClient(amount);
    }

    @GetMapping("/chat-client/list/{amount}")
    public List<Character> generateListOfCharactersChatClient(@PathVariable int amount) {
        if (amount > 10) {
            throw new IllegalArgumentException("Amount must be less than 10");
        }
        return characterService.generateListOfCharactersChatModel(amount);
    }

    // Map of character names and biographies

    @GetMapping("/chat-model/map/{amount}")
    public Map<String, Object> generateListOfCharactersChatModelMap(@PathVariable int amount) {
        if (amount > 10) {
            throw new IllegalArgumentException("Amount must be less than 10");
        }
        return characterService.generateMapOfCharactersChatModel(amount);
    }

    @GetMapping("/chat-client/map/{amount}")
    public Map<String, Object> generateListOfCharactersChatClientMap(@PathVariable int amount) {
        if (amount > 10) {
            throw new IllegalArgumentException("Amount must be less than 10");
        }
        return characterService.generateMapOfCharactersChatClient(amount);
    }

    // List of character names

    @GetMapping("/chat-model/names/{amount}")
    public List<String> generateListOfCharacterNamesChatModel(@PathVariable int amount) {
        return characterService.generateListOfCharacterNamesChatModel(amount);
    }

    @GetMapping("/chat-client/names/{amount}")
    public List<String> generateListOfCharacterNamesChatClient(@PathVariable int amount) {
        return characterService.generateListOfCharacterNamesChatClient(amount);
    }

    // Custom converter

    @GetMapping("/custom-converter/{amount}")
    public Map<String, Character> generateMapOfCharactersCustomConverterGenerics(@PathVariable int amount) {
        return characterService.generateMapOfCharactersCustomConverter(amount);
    }

    @GetMapping("/custom-converter/chat-client/{amount}")
    public Map<String, Character> generateMapOfCharactersCustomConverterChatClient(@PathVariable int amount) {
        return characterService.generateMapOfCharactersCustomConverterChatClient(amount);
    }
}
