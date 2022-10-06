package com.baeldung.componentscan.filter.custom;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

public class ComponentScanCustomFilter implements TypeFilter {

    private static final Logger log = LoggerFactory.getLogger(ComponentScanCustomFilter.class);

    @Override
    public boolean match(MetadataReader metadataReader,
            MetadataReaderFactory metadataReaderFactory) throws IOException {
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        String fullyQualifiedName = classMetadata.getClassName();
        String className = fullyQualifiedName.substring(fullyQualifiedName.lastIndexOf(".") + 1);
        log.info("CN: " + className);
        return className.length() > 5;
    }
}