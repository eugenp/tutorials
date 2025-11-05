package com.baeldung.chatbot.controller;

import com.baeldung.chatbot.service.WhatsAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
public class WhatsAppController {
    
    @Value("${whatsapp.verify_token}")
    private String verifyToken;

    @Autowired
    private WhatsAppService whatsAppService;    

    @PostMapping("/api/whatsapp/send")
    public String sendWhatsAppMessage(@RequestParam String to, @RequestParam String message) {
        whatsAppService.sendWhatsAppMessage(to, message);
        return "Message sent";
    }
    
    @GetMapping("/webhook")
    public String verifyWebhook(@RequestParam("hub.mode") String mode,
                                @RequestParam("hub.verify_token") String token,
                                @RequestParam("hub.challenge") String challenge) {
        if ("subscribe".equals(mode) && verifyToken.equals(token)) {
            return challenge;
        } else {
            return "Verification failed";
        }
    }

    @PostMapping("/webhook")
    public void receiveMessage(@RequestBody String payload) {
        whatsAppService.processIncomingMessage(payload);
    }
}
