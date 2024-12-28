package com.baeldung.springai.anthropic;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
public class ChatBotController {

    private final ChatBotService chatBotService;

    public ChatBotController(ChatBotService chatBotService) {
        this.chatBotService = chatBotService;
    }

    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest chatRequest) {
        ChatResponse chatResponse = chatBotService.chat(chatRequest);
        return ResponseEntity.ok(chatResponse);
    }

    @PostMapping(path = "/multimodal/chat", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ChatResponse> chat(
        @RequestPart(name = "question") String question,
        @RequestPart(name = "chatId", required = false) UUID chatId,
        @ValidFileType @RequestPart(name = "file", required = false) MultipartFile file
    ) {
        ChatRequest chatRequest = new ChatRequest(chatId, question);
        ChatResponse chatResponse = chatBotService.chat(chatRequest, file);
        return ResponseEntity.ok(chatResponse);
    }

}