package com.baeldung.semantic.cache;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "com.baeldung.semantic.cache")
record SemanticCacheProperties(
    Double similarityThreshold,
    String contentField,
    String embeddingField,
    String metadataField
) {}