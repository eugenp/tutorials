package com.baeldung.springbootmvc;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.baeldung.springbootmvc.config.CustomMessageSourceConfiguration;

@RunWith(SpringRunner.class)
@WebMvcTest(value = LoginController.class)
@ContextConfiguration(classes = { SpringBootMvcApplication.class, CustomMessageSourceConfiguration.class })
public class LoginControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenLoginForm_whenEmailFieldNotProvided_testCustomValidMessageIsReturned() throws Exception {

        RequestBuilder builder = MockMvcRequestBuilders.post("/loginform").param("email", "").param("password", "helo");

        // header("accept-language", "fr").
        MvcResult perform = mockMvc.perform(builder).andReturn();
        Assert.assertTrue(perform.getResolvedException().getMessage().contains("valid email"));

    }
}