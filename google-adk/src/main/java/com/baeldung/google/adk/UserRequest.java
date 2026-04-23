package com.baeldung.google.adk;

import jakarta.annotation.Nullable;

import java.util.UUID;

record UserRequest(@Nullable UUID userId, @Nullable UUID sessionId, String question) {
}