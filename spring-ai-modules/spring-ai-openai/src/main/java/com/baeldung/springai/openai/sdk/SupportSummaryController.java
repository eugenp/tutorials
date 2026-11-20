package com.baeldung.springai.openai.sdk;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SupportSummaryController {

    private final SupportSummaryService summaryService;

    public SupportSummaryController(SupportSummaryService summaryService) {
        this.summaryService = summaryService;
    }

    @PostMapping(value = "/support/summary", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public String summarize(@RequestBody String request) {
        return summaryService.summarize(request);
    }
}
