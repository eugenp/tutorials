package com.baeldung.springaistructuredoutput.service;

import java.util.List;
import java.util.Map;

public interface CharacterService {
    Character generateCharacterChatModel(String race);

    Character generateCharacterChatClient(String race);

    List<Character> generateListOfCharactersChatModel(int amount);

    List<Character> generateListOfCharactersChatClient(int amount);

    Map<String, Object> generateMapOfCharactersChatModel(int amount);

    Map<String, Object> generateMapOfCharactersChatClient(int amount);

    List<String> generateListOfCharacterNamesChatModel(int amount);

    List<String> generateListOfCharacterNamesChatClient(int amount);

    Map<String, Character> generateMapOfCharactersCustomConverter(int amount);

    Map<String, Character> generateMapOfCharactersCustomConverterChatClient(int amount);
}
