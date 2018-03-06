package com.midgetontoes.todolist.json;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.module.SimpleSerializers;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class JacksonModule extends SimpleModule {
    private JacksonModule() {
        super("JacksonModule", new Version(1, 0, 0, "", "", ""));
    }

    @Override
    public void setupModule(SetupContext setupContext) {
        SimpleSerializers serializers = new SimpleSerializers();
        serializers.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        setupContext.addSerializers(serializers);
    }
}
