package com.baeldung.springai.transcribe;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "OPENAI_API_KEY", matches = ".*")
class AudioTranscriberLiveTest {

    @Autowired
    private AudioTranscriber audioTranscriber;

    @Test
    void whenTranscribeCalledWithAudioFile_thenCorrectTranscriptionGenerated() throws IOException {
        String audioFileName = "baeldung-audio-description.mp3";
        MultipartFile audioFile = new MockMultipartFile(
            audioFileName,
            null,
            "image/jpeg",
            new DefaultResourceLoader()
                .getResource("classpath:audio/" + audioFileName)
                .getInputStream()
        );

        TranscriptionResponse response = audioTranscriber.transcribe(audioFile);
        assertThat(response)
            .isNotNull()
            .hasNoNullFieldsOrProperties();
        assertThat(response.transcription())
            .isEqualToIgnoringWhitespace("""
                Baeldung is a top-notch educational platform that specializes in Java, Spring, and related technologies.
                It offers a wealth of tutorials, articles, and courses that help developers master programming concepts.
                Known for its clear examples and practical guides, Baeldung is a go-to resource for developers looking 
                to level up their skills.
                """);
    }

}