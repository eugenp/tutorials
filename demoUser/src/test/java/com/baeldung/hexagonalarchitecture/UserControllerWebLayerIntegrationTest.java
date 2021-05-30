package com.baeldung.hexagonalarchitecture;

import com.baeldung.hexagonalarchitecture.controller.UserController;
import com.baeldung.hexagonalarchitecture.entity.User;
import com.baeldung.hexagonalarchitecture.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.spi.json.GsonJsonProvider;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class UserControllerWebLayerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @Test
    public void whenPostUser_thenCreateUser() throws Exception {
        User tushar = new User("tushar");
        given(userService.addUser(Mockito.any(User.class))).willReturn(tushar);

        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(tushar)))
                .andExpect(status().isCreated());
        verify(userService, VerificationModeFactory.times(1)).addUser(Mockito.any());
        reset(userService);
    }


    @Test
    public void whenTestMvcController_thenRetrieveExpectedResult() throws Exception {

        User vaibhav = new User("vaibhav");
        User apeksha = new User("apeksha");

        List<User> allUsers = Arrays.asList(vaibhav, apeksha);

        given(userService.getAllUsers()).willReturn(allUsers);

        mockMvc.perform(get("/user")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(vaibhav.getName())))
                .andExpect(jsonPath("$[1].name", is(apeksha.getName())));
        verify(userService, VerificationModeFactory.times(1)).getAllUsers();
        reset(userService);


    }

    @Test
    public void whenGetUser_thenRetrieveExpectedResult() throws Exception {
        User vaibhav = new User("vaibhav");
        given(userService.getUserById(1)).willReturn(Optional.of(vaibhav));

        mockMvc.perform(get("/user/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", isA(LinkedHashMap.class)))
                .andExpect(jsonPath("$", hasValue(vaibhav.getName())));
    }
}
