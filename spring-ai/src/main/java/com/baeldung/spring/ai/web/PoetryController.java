package com.baeldung.spring.ai.web;

import com.baeldung.spring.ai.dto.PoetryDto;
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

    public static final String WRITE_ME_HAIKU_ABOUT_CAT = "Write me Haiku about cat, haiku should contain word cat";
    private final AiClient aiClient;

    @Autowired
    public PoetryController(AiClient aiClient) {
        this.aiClient = aiClient;
    }

    @GetMapping("/cathaiku")
    public ResponseEntity<String> generateHaiku(){
        return ResponseEntity.ok(aiClient.generate(WRITE_ME_HAIKU_ABOUT_CAT));
    }

    @GetMapping("/poetry")
    public ResponseEntity<PoetryDto> generatePoetry(
            @RequestParam("genre") String genre,
            @RequestParam("theme") String theme
    ){
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
        return ResponseEntity.ok(poetryDtoBeanOutputParser.parse(response.getGeneration().getText()));
    }
}
