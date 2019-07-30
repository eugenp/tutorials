package com.baeldung.hexagonalarchitecture.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.baeldung.hexagonalarchitecture.controller.UserControllerAdapter;
import com.baeldung.hexagonalarchitecture.model.User;
import com.baeldung.hexagonalarchitecture.service.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(UserControllerAdapter.class)
@AutoConfigureMockMvc
public class UserControllerAdapterUnitTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    public void whenGetUserWithID_thenGetCorrectUser() throws Exception {
        User user = new User();
        user.setUserName("baeldung");
        BDDMockito.given(userService.getUser(1L))
            .willReturn(user);
        mvc.perform(get("/user?id=1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.userName", Matchers.is(user.getUserName())));
    }

}
