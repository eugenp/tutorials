package com.baeldung.springai.huggingface.chat;

import java.util.UUID;

public record ChatResponse(UUID chatId, String answer) {
}