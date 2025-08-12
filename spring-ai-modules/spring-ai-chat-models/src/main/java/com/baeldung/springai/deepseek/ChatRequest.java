package com.baeldung.springai.deepseek;

import java.util.UUID;

import org.springframework.lang.Nullable;

record ChatRequest(@Nullable UUID chatId, String question) {
}