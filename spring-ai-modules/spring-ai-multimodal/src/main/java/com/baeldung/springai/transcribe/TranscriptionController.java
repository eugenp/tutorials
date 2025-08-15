package com.baeldung.springai.transcribe;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
class TranscriptionController {

    private final AudioTranscriber audioTranscriber;

    TranscriptionController(AudioTranscriber audioTranscriber) {
        this.audioTranscriber = audioTranscriber;
    }

    @PostMapping("/transcribe")
    ResponseEntity<TranscriptionResponse> transcribe(
        @RequestParam("audioFile") MultipartFile audioFile,
        @RequestParam("context") String context
    ) {
        TranscriptionRequest transcriptionRequest = new TranscriptionRequest(audioFile, context);
        TranscriptionResponse response = audioTranscriber.transcribe(transcriptionRequest);
        return ResponseEntity.ok(response);
    }

}