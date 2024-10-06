package com.baeldung.boot.documentation.springwolf;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.shaded.org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;

@SpringBootTest(classes = {SpringwolfApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EmbeddedKafka(
        partitions = 1, brokerProperties = {
        "listeners=PLAINTEXT://localhost:9092",
        "port=9092",
})
@TestPropertySource(properties = "springwolf.plugin.kafka.publishing.enabled=false") // baeldung spring kafka does not match springwolf kafka
@DirtiesContext
public class ApiIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void asyncApiArtifactTest() throws JSONException, IOException {
        // given
        InputStream s = this.getClass().getResourceAsStream("/asyncapi.json");
        String expected = new String(s.readAllBytes(), StandardCharsets.UTF_8).trim();

        // when
        String actual = restTemplate.getForObject("/springwolf/docs", String.class);

        // then
        Files.writeString(Path.of("src", "test", "resources", "asyncapi.actual.json"), actual);

        JSONAssert.assertEquals(expected, actual, JSONCompareMode.STRICT);
    }
}
