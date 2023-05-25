package com.baeldung.spring.cloud.stream.rabbit.messages;

import org.springframework.messaging.Message;
import org.springframework.messaging.converter.AbstractMessageConverter;
import org.springframework.util.MimeType;

import com.baeldung.spring.cloud.stream.rabbit.model.LogMessage;

public class TextPlainMessageConverter extends AbstractMessageConverter {

    public TextPlainMessageConverter() {
        super(new MimeType("text", "plain"));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return (LogMessage.class == clazz);
    }

    @Override
    protected Object convertFromInternal(Message<?> message, Class<?> targetClass, Object conversionHint) {
        Object payload = message.getPayload();
        String text = payload instanceof String ? (String) payload : new String((byte[]) payload);
        return new LogMessage(text);
    }
}
