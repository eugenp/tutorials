package com.baeldung.custom.deserialization.config;

import org.springframework.boot.web.codec.CodecCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.codec.CodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.util.MimeType;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class CodecCustomizerConfig {

    @Bean
    public CodecCustomizer codecCustomizer(ObjectMapper customObjectMapper) {
        return configurer -> {
            MimeType mimeType = MimeType.valueOf(MediaType.APPLICATION_JSON_VALUE);
            CodecConfigurer.CustomCodecs customCodecs = configurer.customCodecs();
            customCodecs.register(new Jackson2JsonDecoder(customObjectMapper, mimeType));
            customCodecs.register(new Jackson2JsonEncoder(customObjectMapper, mimeType));
        };
    }

}
