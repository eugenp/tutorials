package com.baeldung.imagegen;

record ImageGenerationRequest(
    String prompt,
    String username,
    Integer height,
    Integer width
) {}