package com.baeldung.google.adk;

import java.util.UUID;

record UserResponse(UUID userId, UUID sessionId, String answer) {
}