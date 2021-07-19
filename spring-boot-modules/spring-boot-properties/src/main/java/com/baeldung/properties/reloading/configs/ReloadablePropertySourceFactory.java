package com.baeldung.properties.reloading.configs;

import java.io.IOException;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;

public class ReloadablePropertySourceFactory extends DefaultPropertySourceFactory {
    @Override
    public PropertySource<?> createPropertySource(String s, EncodedResource encodedResource) throws IOException {
        Resource internal = encodedResource.getResource();
        if (internal instanceof FileSystemResource) {
            return new ReloadablePropertySource(s, ((FileSystemResource) internal).getPath());
        }
        if (internal instanceof FileUrlResource) {
            return new ReloadablePropertySource(s, ((FileUrlResource) internal)
              .getURL()
              .getPath());
        }
        return super.createPropertySource(s, encodedResource);
    }
}
