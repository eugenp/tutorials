package com.baeldung.springai.service.impl;

import com.baeldung.springai.dto.PoetryDto;
import com.baeldung.springai.service.PoetryService;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PoetryServiceImpl implements PoetryService {

    public static final String WRITE_ME_HAIKU_ABOUT_CAT = """
        Write me Haiku about cat,
        haiku should start with the word cat obligatory
        """;
    private final ChatModel aiClient;

    @Autowired
    public PoetryServiceImpl(@Qualifier("openAiChatModel") ChatModel aiClient) {
        this.aiClient = aiClient;
    }
    @Override
    public String getCatHaiku() {
        return aiClient.call(WRITE_ME_HAIKU_ABOUT_CAT);
    }

    @Override
    public PoetryDto getPoetryByGenreAndTheme(String genre, String theme) {
        BeanOutputConverter<PoetryDto> outputConverter = new BeanOutputConverter<>(PoetryDto.class);

        String promptString = """
            Write me {genre} poetry about {theme}
            {format}
            """;

        PromptTemplate promptTemplate = new PromptTemplate(promptString);
        promptTemplate.add("genre", genre);
        promptTemplate.add("theme", theme);
        promptTemplate.add("format", outputConverter.getFormat());

        ChatResponse response = aiClient.call(promptTemplate.create());
        return outputConverter.convert(response.getResult().getOutput().getContent());
    }
}
