package com.baeldung.logbook;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.baeldung.extensions.SpringBootLogbackExtensionsApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootLogbackExtensionsApplication.class)
@AutoConfigureMockMvc
public class LogBookUnitTest {

    private static final String LOG_FILE_PATH = "logs/logbook/logback-extension.log";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenHttpRequestCalled_thenRequestDetailsLogged() throws Exception {
        mockMvc.perform(get("/api/hello"))
            .andExpect(status().isOk())
            .andExpect(content().string("Hello, World!"));

        List<String> logLines = Files.readAllLines(Paths.get(LOG_FILE_PATH));
        boolean isLogged = logLines.stream()
            .anyMatch(line -> line.contains("http://localhost/api/hello"));
        assertTrue(isLogged, "Log file should contain log message containing api request details");
    }
}
