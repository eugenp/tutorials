package com.baeldung.springai.transcribe;

import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.stereotype.Service;

@Service
class AudioTranscriber {

    private final OpenAiAudioTranscriptionModel openAiAudioTranscriptionModel;

    AudioTranscriber(OpenAiAudioTranscriptionModel openAiAudioTranscriptionModel) {
        this.openAiAudioTranscriptionModel = openAiAudioTranscriptionModel;
    }

    TranscriptionResponse transcribe(TranscriptionRequest transcriptionRequest) {
        AudioTranscriptionPrompt prompt = new AudioTranscriptionPrompt(
            transcriptionRequest.audioFile().getResource(),
            OpenAiAudioTranscriptionOptions
                .builder()
                .prompt(transcriptionRequest.context())
                .build()
        );
        AudioTranscriptionResponse response = openAiAudioTranscriptionModel.call(prompt);
        return new TranscriptionResponse(response.getResult().getOutput());
    }

}