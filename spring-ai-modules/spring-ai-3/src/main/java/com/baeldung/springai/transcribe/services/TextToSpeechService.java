package com.baeldung.springai.transcribe.services;

import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.ai.openai.OpenAiAudioSpeechOptions;
import org.springframework.ai.openai.audio.speech.Speech;
import org.springframework.ai.openai.audio.speech.SpeechPrompt;
import org.springframework.ai.openai.audio.speech.SpeechResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class TextToSpeechService {

    private OpenAiAudioSpeechModel openAiAudioSpeechModel;

    @Autowired
    public TextToSpeechService(OpenAiAudioSpeechModel openAiAudioSpeechModel) {
        this.openAiAudioSpeechModel = openAiAudioSpeechModel;
    }

    public byte[] makeSpeech(String text) {
        return this.makeSpeech(text, OpenAiAudioSpeechOptions.builder().build());
    }

    public byte[] makeSpeech(String text, OpenAiAudioSpeechOptions speechOptions) {
        SpeechPrompt speechPrompt = new SpeechPrompt(text, speechOptions);

        SpeechResponse response = openAiAudioSpeechModel.call(speechPrompt);

        return response.getResult().getOutput();
    }

    public Flux<byte[]> makeSpeechStream(String text) {
        SpeechPrompt speechPrompt = new SpeechPrompt(text);
        Flux<SpeechResponse> responseStream = openAiAudioSpeechModel.stream(speechPrompt);

        return responseStream
          .map(SpeechResponse::getResult)
          .map(Speech::getOutput);
    }
}