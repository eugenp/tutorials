package com.baeldung.yauaa;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@Import(UserAgentAnalyzerConfiguration.class)
@WebMvcTest(controllers = HomePageController.class)
class HomePageControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String SAFARI_MAC_OS_USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 14_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/18.0 Safari/605.1.15";

    private static final String SAFARI_IOS_USER_AGENT = "Mozilla/5.0 (iPhone; CPU iPhone OS 17_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.0 Mobile/15E148 Safari/604.1";

    @Test
    void whenRequestFromLaptop_thenErrorScreenDisplayed() throws Exception {
        mockMvc.perform(get("/mobile/home")
            .header("User-Agent", SAFARI_MAC_OS_USER_AGENT))
            .andExpect(view().name("error/open-in-mobile"))
            .andExpect(status().isForbidden());
    }

    @Test
    void whenRequestFromMobileDevice_thenHomePageDisplayed() throws Exception {
        mockMvc.perform(get("/mobile/home")
            .header("User-Agent", SAFARI_IOS_USER_AGENT))
            .andExpect(view().name("/mobile-home"))
            .andExpect(status().isOk());
    }

}