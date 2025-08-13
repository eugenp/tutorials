package com.baeldung.springai.imagegen;

record ImageGenerationRequest(
    String prompt,
    String username,
    Integer height,
    Integer width
) {}