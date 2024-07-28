package com.baeldung.boot;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(Controller.class)
public class EmployeeControllerUnitTest {

    @MockBean
    private AppUserRepository appUserRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenAppUser_whenNextIsCalled_ThenReturnAppUser() throws Exception {
        AppUser appUserExpected = new AppUser();
        appUserExpected.setId(1);
        appUserExpected.setName("Test");

        when(appUserRepository.save(any(AppUser.class))).thenReturn(appUserExpected);

        MvcResult result = mockMvc.perform(get("/next").content(objectMapper.writeValueAsString(appUserExpected))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

        AppUser appUser = objectMapper.readValue(result.getResponse()
            .getContentAsString(), AppUser.class);

        assertEquals(appUserExpected.getId(), appUser.getId());
        assertEquals(appUserExpected.getName(), appUser.getName());
        assertNotNull(appUser.getCreatedDate());
    }

    @Test
    public void givenAppUserId_whenFindIsCalled_ThenReturnAppUser() throws Exception {
        AppUser appUserExpected = new AppUser();
        appUserExpected.setId(1);
        appUserExpected.setName("Test");

        when(appUserRepository.findById(1)).thenReturn(Optional.of(appUserExpected));

        MvcResult result = mockMvc.perform(get("/find/1"))
            .andExpect(status().isOk())
            .andReturn();

        AppUser appUser = objectMapper.readValue(result.getResponse()
            .getContentAsString(), AppUser.class);
        assertEquals(appUserExpected.getId(), appUser.getId());
        assertEquals(appUserExpected.getName(), appUser.getName());
        assertNotNull(appUser.getCreatedDate());
    }

}
