package com.baeldung.spring.data.es.config;

import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.node.NodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.baeldung.spring.data.es.repository")
@ComponentScan(basePackages = {"com.baeldung.spring.data.es.service"})
public class Config {

    private static Logger logger = LoggerFactory.getLogger(Config.class);

    @Bean
    public NodeBuilder nodeBuilder() {
        return new NodeBuilder();
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {

        try {
            Path tmpDir = Files.createTempDirectory(Paths.get(System.getProperty("java.io.tmpdir")), "elasticsearch_data");

            ImmutableSettings.Builder elasticsearchSettings = ImmutableSettings.settingsBuilder()
                    .put("http.enabled", "false")
                    .put("path.data", tmpDir.toAbsolutePath().toString());

            logger.debug(tmpDir.toAbsolutePath().toString());

            return new ElasticsearchTemplate(nodeBuilder()
                    .local(true)
                    .settings(elasticsearchSettings.build())
                    .node()
                    .client());
        } catch (IOException ioex) {
            logger.error("Cannot create temp dir", ioex);
            throw new RuntimeException();
        }
    }
}
