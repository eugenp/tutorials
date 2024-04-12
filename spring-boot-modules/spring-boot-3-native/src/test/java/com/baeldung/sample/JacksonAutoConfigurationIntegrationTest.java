package com.baeldung.sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JacksonAutoConfigurationIntegrationTest {

    @Autowired
    ObjectMapper mapper;

    @Test
    void shouldUseSnakeCasePropertyNamingStrategy() {
        assertThat(mapper.getPropertyNamingStrategy())
          .isSameAs(PropertyNamingStrategies.SNAKE_CASE);
    }

}
