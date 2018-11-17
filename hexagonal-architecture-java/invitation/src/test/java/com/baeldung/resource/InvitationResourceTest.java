package com.baeldung.resource;

import com.baeldung.domain.Invitation;
import com.baeldung.service.InvitationService;
import com.baeldung.service.InvitationServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
public class InvitationResourceTest {

    @InjectMocks
    InvitationResource invitationResource;

    private MockMvc mockMvc;

    @Mock
    InvitationService invitationService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.invitationResource).build();
    }

    @Test
    public void givenInvitationIdWhenGetInvitationIsCalledThenReturnInvitation() throws Exception {
        Invitation invitationResponse = new Invitation();
        invitationResponse.setInvitationMessage("You are cordially invited for the birthday party");
        invitationResponse.setInvitationDate("\"01/01/2019\"");


        when(invitationService.getInvitation("1")).thenReturn(invitationResponse);
        this.mockMvc.perform(get("/invitation?invitationId=1").accept(MediaType.parseMediaType("application/json")))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void givenInvitationWhenCreateInvitationIsCalledThenReturnInvitation() throws Exception {

        Invitation invitationResponse = new Invitation();
        invitationResponse.setInvitationMessage("You are cordially invited for the birthday party");
        invitationResponse.setInvitationDate("\"01/01/2019\"");

        when(invitationService.createInvitation(any())).thenReturn(invitationResponse);
        this.mockMvc.perform(post("/invitation").contentType(MediaType.APPLICATION_JSON).content(getJsonContent(invitationResponse)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }

    public static String getJsonContent(Invitation invitationResponse) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(invitationResponse);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
