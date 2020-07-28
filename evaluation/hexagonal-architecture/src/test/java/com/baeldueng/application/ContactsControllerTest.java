package com.baeldueng.application;

import com.baeldueng.domain.Contact;
import com.baeldueng.domain.ContactService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;

@SpringBootTest
class ContactsControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc server;

    @MockBean
    private ContactService contactService;

    @BeforeEach
    public void setup() {
        server = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void givenContactJson_whenInvokingContactsEndpoint_thenInvokeContactService() throws Exception {
        Contact contact = new Contact("name", "mobile");
        String jsonContact = new ObjectMapper().writeValueAsString(contact);

        when(contactService.createContact(contact)).thenReturn(contact);
        server.perform(
                MockMvcRequestBuilders.post("/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContact)
        )
                .andExpect(MockMvcResultMatchers.content().string(jsonContact))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}