package com.architecture.hexagonal.component.impl;

import com.architecture.hexagonal.component.ResourceRetriever;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Component
public class ResourceRetrieverImpl implements ResourceRetriever {

    @Override
    public String retrieveResource(String path) throws IOException {
        try (InputStream inputStream = new ClassPathResource(path).getInputStream()) {
            byte[] byteData = FileCopyUtils.copyToByteArray(inputStream);
            return new String(byteData, StandardCharsets.UTF_8);
        }
    }
}