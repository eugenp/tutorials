package com.baeldung.architecture.hexagonal.infrastructure.notifications;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageDto {
    private final String user;
    private final String message;

    public static MessageDtoBuilder builder() {
        return new MessageDtoBuilder();
    }
}
