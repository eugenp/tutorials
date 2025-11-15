package com.baeldung.springai.transcribe.controllers;

import com.baeldung.springai.transcribe.services.TextToSpeechService;
import org.springframework.ai.openai.OpenAiAudioSpeechOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Map;

@RestController
public class TextToSpeechController {
    private final TextToSpeechService textToSpeechService;

    @Autowired
    public TextToSpeechController(TextToSpeechService textToSpeechService) {
        this.textToSpeechService = textToSpeechService;
    }

    @GetMapping("/text-to-speech-customized")
    public ResponseEntity<byte[]> generateSpeechForTextCustomized(@RequestParam("text") String text, @RequestParam Map<String, String> params) {
        OpenAiAudioSpeechOptions speechOptions = OpenAiAudioSpeechOptions.builder()
          .model(params.get("model"))
          .voice(OpenAiAudioApi.SpeechRequest.Voice.valueOf(params.get("voice")))
          .responseFormat(OpenAiAudioApi.SpeechRequest.AudioResponseFormat.valueOf(params.get("responseFormat")))
          .speed(Float.parseFloat(params.get("speed")))
          .build();

        return ResponseEntity.ok(textToSpeechService.makeSpeech(text, speechOptions));
    }

    @GetMapping("/text-to-speech")
    public ResponseEntity<byte[]> generateSpeechForText(@RequestParam("text") String text) {
        return ResponseEntity.ok(textToSpeechService.makeSpeech(text));
    }

    @GetMapping(value = "/text-to-speech-stream", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<StreamingResponseBody> streamSpeech(@RequestParam("text") String text) {
        Flux<byte[]> audioStream = textToSpeechService.makeSpeechStream(text);

        StreamingResponseBody responseBody = outputStream -> {
            audioStream.toStream().forEach(bytes -> {
                try {
                    outputStream.write(bytes);
                    outputStream.flush();
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            });
        };

        return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_OCTET_STREAM)
          .body(responseBody);
    }

}
