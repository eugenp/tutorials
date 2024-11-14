package com.baeldung.springai.chromadb;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.chromadb.ChromaDBContainer;
import org.testcontainers.ollama.OllamaContainer;

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

    @Bean
    @ServiceConnection
    ChromaDBContainer chromaDB() {
        return new ChromaDBContainer("ghcr.io/chroma-core/chroma:0.4.15");
    }

    @Bean
    @ServiceConnection
    OllamaContainer ollama() {
        return new OllamaContainer("ollama/ollama:0.4.1");
    }

}