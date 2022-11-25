package com.baeldung.enummapping;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.baeldung.enummapping.controllers.EnumMappingController;
import com.baeldung.enummapping.enums.Level;

@RunWith(SpringRunner.class)
@WebMvcTest(EnumMappingController.class)
public class EnumMappingIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenPassingLowerCaseEnumConstant_thenConvert() throws Exception {
        mockMvc.perform(get("/enummapping/get?level=medium"))
            .andExpect(status().isOk())
            .andExpect(content().string(Level.MEDIUM.name()));
    }

    @Test
    public void whenPassingUnknownEnumConstant_thenReturnUndefined() throws Exception {
        mockMvc.perform(get("/enummapping/get?level=unknown"))
            .andExpect(status().isOk())
            .andExpect(content().string("undefined"));
    }

    @Test
    public void whenPassingEmptyParameter_thenReturnUndefined() throws Exception {
        mockMvc.perform(get("/enummapping/get?level="))
            .andExpect(status().isOk())
            .andExpect(content().string("undefined"));
    }

    @Test
    public void whenPassingNoParameter_thenReturnUndefined() throws Exception {
        mockMvc.perform(get("/enummapping/get"))
            .andExpect(status().isOk())
            .andExpect(content().string("undefined"));
    }

}
