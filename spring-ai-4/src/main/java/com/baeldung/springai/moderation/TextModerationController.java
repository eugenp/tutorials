package com.baeldung.springai.moderation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TextModerationController {

    private final TextModerationService service;

    @Autowired
    public TextModerationController(TextModerationService service) {
        this.service = service;
    }

    @PostMapping("/moderate")
    public ResponseEntity<String> moderate(@RequestBody ModerateRequest request) {
        return ResponseEntity.ok(service.moderate(request.getText()));
    }
}
