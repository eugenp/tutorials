package com.baeldung.google.adk;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/baelgent/interact")
class BaelgentController {

    private final BaelgentService baelgentService;

    BaelgentController(BaelgentService baelgentService) {
        this.baelgentService = baelgentService;
    }

    @PostMapping
    BaelgentResponse interact(@RequestBody BaelgentRequest request) {
        return baelgentService.interact(request);
    }

}