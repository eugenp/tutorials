package com.baeldung.springai.transcribe;

import com.baeldung.springai.transcribe.services.TextToSpeechService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Flux;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@AutoConfigureWebTestClient
@EnabledIfEnvironmentVariable(named = "OPENAI_API_KEY", matches = ".*")
class TextToSpeechLiveTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TextToSpeechService textToSpeechService;

    @Test
    void givenTextToSpeechService_whenCallingTextToSpeechEndpoint_thenExpectedAudioFileBytesShouldBeObtained() throws Exception {
        byte[] audioContent = mockMvc.perform(get("/text-to-speech")
          .param("text", "Hello from Baeldung"))
          .andExpect(status().isOk())
          .andReturn()
          .getResponse()
          .getContentAsByteArray();

        assertNotEquals(0, audioContent.length);
    }

    @Test
    void givenTextToSpeechService_whenCallingTextToSpeechEndpointWithAnotherVoiceOption_thenExpectedAudioFileBytesShouldBeObtained() throws Exception {
        byte[] audioContent = mockMvc.perform(get("/text-to-speech-customized")
          .param("text", "Hello from Baeldung")
          .param("model", "tts-1")
          .param("voice", "NOVA")
          .param("responseFormat", "MP3")
          .param("speed", "1.0"))
          .andExpect(status().isOk())
          .andReturn()
          .getResponse()
          .getContentAsByteArray();

        assertNotEquals(0, audioContent.length);
    }

    @Test
    void givenStreamingEndpoint_whenCalled_thenReceiveAudioFileBytes() throws Exception {

        String longText = """
          Hello from Baeldung!
          Here, we explore the world of Java,
          Spring, and web development with clear, practical tutorials.
          Whether you're just starting out or diving deep into advanced
          topics, you'll find guides to help you write clean, efficient,
          and modern code.
          """;

        mockMvc.perform(get("/text-to-speech-stream")
            .param("text", longText)
            .accept(MediaType.APPLICATION_OCTET_STREAM))
          .andExpect(status().isOk())
          .andDo(result -> {
              waitUntilResponseIsReady();
              byte[] response = result.getResponse().getContentAsByteArray();
              assertNotNull(response);
              assertTrue( response.length > 0);
          });
    }

    private static void waitUntilResponseIsReady() throws InterruptedException {
        Thread.sleep(5000);
    }
}