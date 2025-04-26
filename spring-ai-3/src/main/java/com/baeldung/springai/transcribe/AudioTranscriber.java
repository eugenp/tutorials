package com.baeldung.springai.transcribe;

import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
class AudioTranscriber {

    private final OpenAiAudioTranscriptionModel openAiAudioTranscriptionModel;

    AudioTranscriber(OpenAiAudioTranscriptionModel openAiAudioTranscriptionModel) {
        this.openAiAudioTranscriptionModel = openAiAudioTranscriptionModel;
    }

    TranscriptionResponse transcribe(MultipartFile audioFile) {
        String transcription = openAiAudioTranscriptionModel.call(audioFile.getResource());
        return new TranscriptionResponse(transcription);
    }

}