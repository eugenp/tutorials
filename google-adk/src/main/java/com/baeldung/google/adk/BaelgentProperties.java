package com.baeldung.google.adk;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

@ConfigurationProperties(prefix = "com.baeldung.agent")
record BaelgentProperties(
    String name,
    String description,
    String aiModel,
    Resource instruction
) {}