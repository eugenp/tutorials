package com.baeldung.springai.agentskills.anthropic;

import javax.validation.Valid;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agent-skills")
@Validated
public class AgentSkillsController {

    private final AgentSkillsService agentSkillsService;

    public AgentSkillsController(AgentSkillsService agentSkillsService) {
        this.agentSkillsService = agentSkillsService;
    }

    @PostMapping("/report")
    public ResponseEntity<byte[]> genReport(@RequestBody @Valid ReportRequest reportRequest) {
        AnthropicDocument document = agentSkillsService.genReport(reportRequest);
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(document.mimeType()))
            .header(HttpHeaders.CONTENT_DISPOSITION,
                ContentDisposition.attachment()
                    .filename(document.fileName())
                    .build()
                    .toString())
            .body(document.content());
    }

}
