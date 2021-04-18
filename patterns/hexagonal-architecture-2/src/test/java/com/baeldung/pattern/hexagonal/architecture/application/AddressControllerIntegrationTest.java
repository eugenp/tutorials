package com.baeldung.pattern.hexagonal.architecture.application;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.baeldung.pattern.hexagonal.architecture.HexagonalArchitectureApplication;
import com.baeldung.pattern.hexagonal.architecture.application.dto.AddressDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = { HexagonalArchitectureApplication.class })
@WebAppConfiguration
class AddressControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
            .build();
    }

    @Test
    void whenPostAddress_thenReturnAddress() throws Exception {
        // given

        AddressDto addressDto = createAddressDto();
        ObjectMapper mapper = new ObjectMapper();
        String addressString = mapper.writeValueAsString(addressDto);

        // when

        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.post("/api/addresses")
            .content(addressString)
            .contentType(MediaType.APPLICATION_JSON));

        // then

        result.andExpect(status().isOk())
            .andExpect(jsonPath("$.city").value(addressDto.getCity()))
            .andExpect(jsonPath("$.house").value(addressDto.getHouse()))
            .andExpect(jsonPath("$.street").value(addressDto.getStreet()))
            .andExpect(jsonPath("$.zip").value(addressDto.getZip()))
            .andReturn();
    }

    private AddressDto createAddressDto() {
        return AddressDto.builder()
            .name("house")
            .city("Warsaw")
            .house("6")
            .street("Test")
            .zip("12-111")
            .build();
    }
}
