package com.baeldung.encoding;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PasswordEncodingUnitTest {
    private final static String userName = "baeldung";
    private final static String passwordDecoded = "baeldungPassword";

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
          .apply(springSecurity())
          .build();
    }

    @Test
    public void givenRequestWithWrongPassword_shouldFailWith401() throws Exception {
        mvc.perform(get("/").with(httpBasic(userName, "wrongPassword")))
          .andExpect(status().isUnauthorized());

    }

    @Test
    public void givenRequestWithCorrectDecodedPassword_houldSucceedWith200() throws Exception {
        mvc.perform(get("/").with(httpBasic(userName, passwordDecoded)))
          .andExpect(status().isOk());

    }
}