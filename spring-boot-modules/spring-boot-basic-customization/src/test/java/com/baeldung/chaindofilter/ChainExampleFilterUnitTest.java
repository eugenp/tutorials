package com.baeldung.chaindofilter;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ChainExampleFilterUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenHeaderInResponse_whenFilterDoChain_thenAssertHeaderPresent() throws Exception {
        mockMvc.perform(get("/chain/do-filter"))
            .andExpect(status().isOk())
            .andExpect(header().string("Custom-Header-Key", "CUSTOM_HEADER_VALUE"));
    }

}
