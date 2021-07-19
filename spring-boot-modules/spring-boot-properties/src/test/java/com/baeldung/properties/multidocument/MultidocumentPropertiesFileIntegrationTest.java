package com.baeldung.properties.multidocument;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("default")
@SpringBootTest(classes = { MultidocumentTestConfig.class })
public class MultidocumentPropertiesFileIntegrationTest {

    Logger logger = LoggerFactory.getLogger(MultidocumentPropertiesFileIntegrationTest.class);

    @Value("${baeldung.customProperty}")
    private String customProperty;

    @Test
    public void givenMultidocumentPropertiesFileWhenBootContextLoadedThenDocumentProcessedCorrectly() {
        assertThat(customProperty).isEqualTo("valueDefault");
    }
}
