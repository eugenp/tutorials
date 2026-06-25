package com.baeldung.handlerinterceptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = DeliveryChargesController.class)
class DeliveryChargeInterceptorIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeliveryChargeService deliveryChargeService;

    @MockBean
    private FeatureFlagService featureFlagService;

    @Test
    void whenRolloutIsZero_thenV1IsUsed() throws Exception {
        when(featureFlagService.rolloutPercentage()).thenReturn(0);
        when(deliveryChargeService.calculateV1("SW1A")).thenReturn(5.0);

        mockMvc.perform(post("/delivery-charges/calculate").param("postcode", "SW1A"))
            .andExpect(status().isOk())
            .andExpect(content().string("5.0"));
    }

    @Test
    void whenRolloutIs100_thenV2IsUsed() throws Exception {
        when(featureFlagService.rolloutPercentage()).thenReturn(100);
        when(deliveryChargeService.calculateV2("SW1A")).thenReturn(3.5);

        mockMvc.perform(post("/delivery-charges/calculate").param("postcode", "SW1A"))
            .andExpect(status().isOk())
            .andExpect(content().string("3.5"));
    }

    @Test
    void whenRolloutIs50_thenBothVersionsAreUsed() throws Exception {
        when(featureFlagService.rolloutPercentage()).thenReturn(50);
        when(deliveryChargeService.calculateV1("SW1A")).thenReturn(5.0);
        when(deliveryChargeService.calculateV2("SW1A")).thenReturn(3.5);

        int v1Count = 0;
        int v2Count = 0;

        for (int i = 0; i < 20; i++) {
            String response = mockMvc.perform(post("/delivery-charges/calculate").param("postcode", "SW1A"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
            if ("5.0".equals(response)) {
                v1Count++;
            } else if ("3.5".equals(response)) {
                v2Count++;
            }
        }

        assertThat(v1Count).isGreaterThan(0);
        assertThat(v2Count).isGreaterThan(0);
    }
}
