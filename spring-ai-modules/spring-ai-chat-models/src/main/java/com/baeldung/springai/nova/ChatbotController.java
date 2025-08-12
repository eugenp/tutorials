package com.baeldung.springai.nova;

import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ChatbotController {

    private final ChatbotService chatbotService;

    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest chatRequest) {
        ChatResponse chatResponse = chatbotService.chat(chatRequest);
        return ResponseEntity.ok(chatResponse);
    }

    @PostMapping(path = "/multimodal/chat", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ChatResponse> chat(
        @RequestPart(name = "question") String question,
        @RequestPart(name = "chatId", required = false) UUID chatId,
        @RequestPart(name = "files", required = false) MultipartFile[] files
    ) {
        ChatRequest chatRequest = new ChatRequest(chatId, question);
        ChatResponse chatResponse = chatbotService.chat(chatRequest, files);
        return ResponseEntity.ok(chatResponse);
    }

}