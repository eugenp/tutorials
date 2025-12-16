package com.baeldung.google.adk;

import jakarta.annotation.Nullable;

import java.util.UUID;

record BaelgentRequest(@Nullable UUID userId, @Nullable UUID sessionId, String question) {
}