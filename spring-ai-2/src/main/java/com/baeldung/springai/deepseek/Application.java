package com.baeldung.springai.deepseek;

import org.springframework.ai.autoconfigure.anthropic.AnthropicAutoConfiguration;
import org.springframework.ai.autoconfigure.vectorstore.chroma.ChromaVectorStoreAutoConfiguration;
import org.springframework.ai.autoconfigure.vectorstore.pgvector.PgVectorStoreAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * Excluding the below auto-configurations to avoid start up
 * failure. Their corresponding starters are present on the classpath but are
 * only needed by other articles in the shared codebase.
 */
@SpringBootApplication(exclude = {
    AnthropicAutoConfiguration.class,
    PgVectorStoreAutoConfiguration.class,
    ChromaVectorStoreAutoConfiguration.class
})
@PropertySource("classpath:application-deepseek.properties")
class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}