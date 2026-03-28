package com.baeldung.springai.agentskills.anthropic;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(properties = {
    "spring.profiles.active=test",
    "spring.ai.anthropic.api-key=test-key"
})
@AutoConfigureMockMvc
class AgentSkillsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AgentSkillsService agentSkillsService;

    @Test
    void whenReportRequestIsValid_thenEndpointReturnsGeneratedDocument() throws Exception {
        byte[] documentContent = "%PDF-1.7\nMock PDF".getBytes();
        ReportRequest reportRequest = new ReportRequest("Generate a monthly sales summary");
        AnthropicDocument generatedDocument = new AnthropicDocument("sales-report.pdf", documentContent);

        when(agentSkillsService.genReport(reportRequest)).thenReturn(generatedDocument);

        mockMvc.perform(get("/agent-skills/report")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "prompt": "Generate a monthly sales summary"
                    }
                    """))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_PDF))
            .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"sales-report.pdf\""))
            .andExpect(content().bytes(documentContent));

        verify(agentSkillsService).genReport(reportRequest);
    }

}
