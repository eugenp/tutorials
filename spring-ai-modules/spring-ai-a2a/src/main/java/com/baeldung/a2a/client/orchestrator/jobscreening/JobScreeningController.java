package com.baeldung.a2a.client.orchestrator.jobscreening;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class JobScreeningController {

    private final ChatClient chatClient;

    JobScreeningController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @PostMapping("/screenings")
    ScreeningResponse screenCandidate(@RequestBody ScreeningRequest screeningRequest) {
        String verdict = chatClient
            .prompt()
            .user(screeningRequest.toString())
            .call()
            .content();
        return new ScreeningResponse(verdict);
    }

    record ScreeningRequest(
        String jobTitle,
        String requiredSkills,
        String candidateSkills,
        int expectedSalary
    ) {}

    record ScreeningResponse(
        String verdict
    ) {}
}