package com.baeldung.removingroleprefix;

import com.baeldung.removingroleprefix.config.MethodSecurityJavaConfig;
import com.baeldung.removingroleprefix.config.UserDetailsConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.baeldung.removingroleprefix.TestUtils.basicAuthHeader;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(controllers = TestSecuredController.class)
@ContextConfiguration(classes = { MethodSecurityJavaConfig.class, UserDetailsConfig.class,
        TestSecuredController.class })
public class RemovingRolePrefixMethodSecurityIntegrationTest {

    @Autowired
    WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
          .webAppContextSetup(wac)
          .apply(SecurityMockMvcConfigurers.springSecurity())
          .build();
    }

    @Test
    public void givenMethodSecurityJavaConfig_whenCallTheResourceWithAdminRole_thenOkResponseCodeExpected() throws Exception {
        MockHttpServletRequestBuilder with = MockMvcRequestBuilders.get("/test-resource-method-security-resource")
          .header("Authorization", basicAuthHeader("admin", "password"));

        ResultActions performed = mockMvc.perform(with);

        MvcResult mvcResult = performed.andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
    }
}
