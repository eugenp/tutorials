package com.baeldung.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.Collection;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerModelAttributeIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenUrlEncodedFormData_whenAddEmployeeEndpointCalled_thenModelContainsMsgAttribute() throws Exception {
        Collection<NameValuePair> formData = Arrays.asList(new BasicNameValuePair("name", "employeeName"), new BasicNameValuePair("id", "99"), new BasicNameValuePair("contactNumber", "123456789"));
        String urlEncodedFormData = EntityUtils.toString(new UrlEncodedFormEntity(formData));

        mockMvc.perform(post("/addEmployee").contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .content(urlEncodedFormData))
            .andExpect(status().isOk())
            .andExpect(view().name("employeeView"))
            .andExpect(model().attribute("msg", "Welcome to the Netherlands!"));
    }
}
