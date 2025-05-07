package com.baeldung.springai.groq;

import org.springframework.ai.autoconfigure.openai.OpenAiAutoConfiguration;
import org.springframework.ai.autoconfigure.vectorstore.chroma.ChromaVectorStoreAutoConfiguration;
import org.springframework.ai.autoconfigure.vectorstore.pgvector.PgVectorStoreAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(exclude = {
    OpenAiAutoConfiguration.class,
    PgVectorStoreAutoConfiguration.class,
    ChromaVectorStoreAutoConfiguration.class
})
@PropertySource("classpath:application-groq.properties")
public class GroqApplication {

    public static void main(String[] args) {
        SpringApplication.run(GroqApplication.class, args);
    }

}