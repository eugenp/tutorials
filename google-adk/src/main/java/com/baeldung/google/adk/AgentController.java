package com.baeldung.google.adk;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/agent/interact")
class AgentController {

    private final AgentService agentService;

    AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    @PostMapping
    UserResponse interact(@RequestBody UserRequest request) {
        return agentService.interact(request);
    }
}