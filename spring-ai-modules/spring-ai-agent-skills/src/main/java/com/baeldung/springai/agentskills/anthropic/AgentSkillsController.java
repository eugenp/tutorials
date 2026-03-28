package com.baeldung.springai.agentskills.anthropic;

import javax.validation.Valid;

import org.apache.tika.Tika;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agent-skills")
@Validated
public class AgentSkillsController {

    private final AgentSkillsService agentSkillsService;
    private final Tika tika = new Tika();

    public AgentSkillsController(AgentSkillsService agentSkillsService) {
        this.agentSkillsService = agentSkillsService;
    }

    @GetMapping("/report")
    public ResponseEntity<byte[]> genReport(@RequestBody @Valid ReportRequest reportRequest) {
        AnthropicDocument document = agentSkillsService.genReport(reportRequest);
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(tika.detect(document.content())))
            .header(HttpHeaders.CONTENT_DISPOSITION,
                ContentDisposition.attachment()
                    .filename(document.fileName())
                    .build()
                    .toString())
            .body(document.content());
    }

}
