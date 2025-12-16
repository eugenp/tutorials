package com.baeldung.google.adk;

import java.util.UUID;

record BaelgentResponse(UUID userId, UUID sessionId, String answer) {
}