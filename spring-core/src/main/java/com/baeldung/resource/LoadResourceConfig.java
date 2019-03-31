package com.baeldung.resource;

import com.google.common.io.CharStreams;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

@Configuration
public class LoadResourceConfig {
    @Value("classpath:resource.txt")
    private Resource resource;

    @Value("#{T(org.apache.commons.io.FileUtils).readFileToString(" + "T(org.springframework.util.ResourceUtils).getFile('classpath:resource.txt')" + ")}")
    private String resourceStringUsingSpel;

    @Bean
    public String resourceStringUsingSpel() throws IOException {
        return resourceStringUsingSpel;
    }

    @Bean
    public String resourceStringUsingFileCopyUtils() throws IOException {
        final Reader reader = new InputStreamReader(resource.getInputStream());
        return FileCopyUtils.copyToString(reader);
    }

    @Bean
    public String resourceStringUsingStreamUtils() throws IOException {
        try (InputStream inputStream = resource.getInputStream()) {
            return StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        }
    }

    @Bean
    public String resourceStringUsingGuava() throws IOException {
        try (final Reader reader = new InputStreamReader(resource.getInputStream())) {
            return CharStreams.toString(reader);
        }
    }

    @Bean
    public String resourceStringUsingCommonsIo() throws IOException {
        return FileUtils.readFileToString(resource.getFile(), StandardCharsets.UTF_8);
    }
}
