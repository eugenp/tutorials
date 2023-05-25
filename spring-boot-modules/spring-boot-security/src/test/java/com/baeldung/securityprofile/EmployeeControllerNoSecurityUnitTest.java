package com.baeldung.securityprofile;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = EmployeeController.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = { Application.class, ApplicationNoSecurity.class })
public class EmployeeControllerNoSecurityUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenSecurityDisabled_shouldBeOk() throws Exception {
        this.mockMvc.perform(get("/employees"))
                .andExpect(status().isOk());
    }

}