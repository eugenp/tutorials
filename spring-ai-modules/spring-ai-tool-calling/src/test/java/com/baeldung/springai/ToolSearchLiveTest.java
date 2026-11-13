package com.baeldung.springai;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.baeldung.springai.service.TravelAssistantService;

@ActiveProfiles("tool-search")
@SpringBootTest
@EnabledIfEnvironmentVariable(named = "OPENAI_API_KEY", matches = ".*")
class ToolSearchLiveTest {

    @Autowired
    private TravelAssistantService travelAssistantService;

    @Test
    void givenToolSearchAdvisor_whenAskingAboutWeather_thenModelDiscoversWeatherTool() {
        String answer = travelAssistantService.planTripInSession("travel-session-42",
            "Help me plan a trip to Amsterdam. What is the current weather there?");

        assertThat(answer).containsIgnoringCase("sunny");
    }
}
