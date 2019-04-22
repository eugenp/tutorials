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

    @Bean
    public String resourceStringUsingFileCopyUtils() throws IOException {
        try (InputStream inputStream = resource.getInputStream();
             Reader reader = new InputStreamReader(inputStream);
        ) {
            return FileCopyUtils.copyToString(reader);
        }
    }

    @Bean
    public String resourceStringUsingStreamUtils() throws IOException {
        try (InputStream inputStream = resource.getInputStream()) {
            return StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        }
    }
}
