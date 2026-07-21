package com.baeldung.httplogging;

import java.util.UUID;

public record BookCreatedResponse(UUID id, String title, String author) { }
