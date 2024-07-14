package com.baeldung.twilio.whatsapp;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ReplyController {

    private final WhatsAppMessageDispatcher whatsAppMessageDispatcher;

    @PostMapping(value = "/api/v1/whatsapp-message-reply")
    public ResponseEntity<Void> reply(@RequestParam("ProfileName") String username,
            @RequestParam("WaId") String phoneNumber) {
        whatsAppMessageDispatcher.dispatchReplyMessage(phoneNumber, username);
        return ResponseEntity.ok().build();
    }

}
