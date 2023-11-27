package com.baeldung.spring.ai.service.impl;

import com.baeldung.spring.ai.dto.PoetryDto;
import com.baeldung.spring.ai.service.PoetryService;
import org.springframework.ai.client.AiClient;
import org.springframework.ai.client.AiResponse;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.ai.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
public class PoetryServiceImpl implements PoetryService {

    public static final String WRITE_ME_HAIKU_ABOUT_CAT = "Write me Haiku about cat, haiku should contain word cat";
    private final AiClient aiClient;

    @Autowired
    public PoetryServiceImpl(AiClient aiClient) {
        this.aiClient = aiClient;
    }
    @Override
    public String getCatHaiku() {
        return aiClient.generate(WRITE_ME_HAIKU_ABOUT_CAT);
    }

    @Override
    public PoetryDto getPoetryByGenreAndTheme(String genre, String theme) {
        BeanOutputParser<PoetryDto> poetryDtoBeanOutputParser = new BeanOutputParser<>(PoetryDto.class);

        String promtString = """
                Write me {genre} poetry about {theme}
                {format}
                """;

        PromptTemplate promptTemplate = new PromptTemplate(promtString);
        promptTemplate.add("genre", genre);
        promptTemplate.add("theme", theme);
        promptTemplate.add("format", poetryDtoBeanOutputParser.getFormat());

        promptTemplate.setOutputParser(poetryDtoBeanOutputParser);

        AiResponse response = aiClient.generate(promptTemplate.create());
        return poetryDtoBeanOutputParser.parse(response.getGeneration().getText());
    }
}
