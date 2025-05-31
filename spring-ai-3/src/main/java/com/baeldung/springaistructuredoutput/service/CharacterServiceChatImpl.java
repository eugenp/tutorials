package com.baeldung.springaistructuredoutput.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.stereotype.Service;

import com.baeldung.springaistructuredoutput.converters.GenericMapOutputConverter;

import java.util.List;
import java.util.Map;

@Service
public class CharacterServiceChatImpl implements CharacterService {

    private final ChatModel chatModel;

    @Autowired
    public CharacterServiceChatImpl(@Qualifier("openAiChatModel") ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @Override
    public Character generateCharacterChatModel(String race) {
        BeanOutputConverter<Character> beanOutputConverter = new BeanOutputConverter<>(Character.class);

        String format = beanOutputConverter.getFormat();

        String template = """
            Generate a D&D character with race {race}
            {format}
            """;

        PromptTemplate promptTemplate = new PromptTemplate(template, Map.of("race", race, "format", format));
        Prompt prompt = new Prompt(promptTemplate.createMessage());
        Generation generation = chatModel.call(prompt).getResult();

        return beanOutputConverter.convert(generation.getOutput().getText());
    }

    @Override
    public Character generateCharacterChatClient(String race) {
        return ChatClient.create(chatModel)
          .prompt()
          .user(spec -> spec.text("Generate a D&D character with race {race}")
            .param("race", race))
          .call()
          .entity(Character.class);
    }

    @Override
    public List<Character> generateListOfCharactersChatClient(int amount) {
        return ChatClient.create(chatModel).prompt()
          .user("Generate " + amount + "D&D characters with random races")
          .call()
          .entity(new ParameterizedTypeReference<>() {});
    }

    @Override
    public List<Character> generateListOfCharactersChatModel(int amount) {
        BeanOutputConverter<List<Character>> outputConverter = new BeanOutputConverter<>(
          new ParameterizedTypeReference<>() {});
        String format = outputConverter.getFormat();
        String template = """
            "Generate {amount} D&D characters with random races"
            {format}
            """;
        Prompt prompt = new Prompt(new PromptTemplate(template, Map.of("amount",
          String.valueOf(amount), "format", format)).createMessage());

        Generation generation = chatModel.call(prompt).getResult();

        return outputConverter.convert(generation.getOutput().getText());
    }

    @Override
    public Map<String, Object> generateMapOfCharactersChatClient(int amount) {
        return ChatClient.create(chatModel).prompt()
          .user(u -> u.text("Generate {amount} D&D characters, where key is a character's name")
            .param("amount", String.valueOf(amount)))
          .call()
          .entity(new ParameterizedTypeReference<Map<String, Object>>() {});
    }

    @Override
    public Map<String, Object> generateMapOfCharactersChatModel(int amount) {
        MapOutputConverter outputConverter = new MapOutputConverter();
        String format = outputConverter.getFormat();
        String template = """
            "Generate {amount} of key-value pairs, where key is a "Dungeons and Dragons" character name and value (String) is his bio.
            {format}
            """;
        Prompt prompt = new Prompt(new PromptTemplate(template, Map.of("amount", String.valueOf(amount), "format", format)).createMessage());
        Generation generation = chatModel.call(prompt).getResult();

        return outputConverter.convert(generation.getOutput().getText());
    }

    @Override
    public List<String> generateListOfCharacterNamesChatClient(int amount) {
        return ChatClient.create(chatModel).prompt()
          .user(u -> u.text("List {amount} D&D character names")
            .param("amount", String.valueOf(amount)))
          .call()
          .entity(new ListOutputConverter(new DefaultConversionService()));
    }

    @Override
    public List<String> generateListOfCharacterNamesChatModel(int amount) {
        ListOutputConverter listOutputConverter = new ListOutputConverter(new DefaultConversionService());
        String format = listOutputConverter.getFormat();
        String userInputTemplate = """
            List {amount} D&D character names
            {format}
            """;
        PromptTemplate promptTemplate = new PromptTemplate(userInputTemplate,
          Map.of("amount", amount, "format", format));
        Prompt prompt = new Prompt(promptTemplate.createMessage());
        Generation generation = chatModel.call(prompt).getResult();
        return listOutputConverter.convert(generation.getOutput().getText());
    }

    @Override
    public Map<String, Character> generateMapOfCharactersCustomConverter(int amount) {
        GenericMapOutputConverter<Character> outputConverter = new GenericMapOutputConverter<>(Character.class);
        String format = outputConverter.getFormat();
        String template = """
            "Generate {amount} of key-value pairs, where key is a "Dungeons and Dragons" character name and value is character object.
            {format}
            """;
        Prompt prompt = new Prompt(new PromptTemplate(template, Map.of("amount", String.valueOf(amount), "format", format)).createMessage());
        Generation generation = chatModel.call(prompt).getResult();

        return outputConverter.convert(generation.getOutput().getText());
    }

    @Override
    public Map<String, Character> generateMapOfCharactersCustomConverterChatClient(int amount) {
        return ChatClient.create(chatModel).prompt()
          .user(u -> u.text("Generate {amount} D&D characters, where key is a character's name")
            .param("amount", String.valueOf(amount)))
          .call()
          .entity(new GenericMapOutputConverter<>(Character.class));
    }
}
