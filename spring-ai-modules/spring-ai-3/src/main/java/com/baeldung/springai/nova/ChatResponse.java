package com.baeldung.springai.nova;

import java.util.UUID;

public record ChatResponse(UUID chatId, String answer) {
}