package com.baeldung.hexagonalarchitecture;

import net.minidev.json.JSONValue;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.JVM)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenCallEndPointToRetriveAllUsers_ShouldBeReturnAllUsers_WithStatusOk() throws Exception {
        final MvcResult mvcResult = mockMvc.perform(get("/users")).andExpect(status().isOk()).andReturn();
        JSONAssert.assertEquals(getJsonFileAsString("expected/all_users.json"), getMvcResultAsString(mvcResult), true);
    }

    @Test
    public void whemCallEndPointToRetriveSingleUser_ShouldBeReturnInformationFromOnlyOneUser_WithStatusOk() throws Exception {
        final MvcResult mvcResult = mockMvc.perform(get("/users/1")).andExpect(status().isOk()).andReturn();
        JSONAssert.assertEquals(getJsonFileAsString("expected/user.json"), getMvcResultAsString(mvcResult), true);
    }

    @Test
    public void contextLoads3() throws Exception {
        final MvcResult mvcResult =
                mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON_UTF8).content(getJsonFileAsString("request/create_user.json")))
                        .andExpect(status().isOk()).andReturn();
        JSONAssert.assertEquals(getJsonFileAsString("expected/user_created.json"), getMvcResultAsString(mvcResult), true);
    }

    @Test
    public void contextLoads4() throws Exception {
        mockMvc.perform(get("/users/10")).andExpect(status().isNotFound()).andReturn();
    }

    @Test
    public void contextLoads5() throws Exception {
        mockMvc.perform(delete("/users/1")).andExpect(status().isPreconditionFailed()).andReturn();
    }

    @Test
    public void whenCallEndPointToDeleteUser_WithValidStatus_StatusOfReturnShouldBeNoContent() throws Exception {
        mockMvc.perform(delete("/users/2")).andExpect(status().isNoContent()).andReturn();
    }

    private String getMvcResultAsString(final MvcResult mvcResult) throws UnsupportedEncodingException {
        return mvcResult.getResponse().getContentAsString();
    }

    private String getJsonFileAsString(final String nameFile) throws UnsupportedEncodingException {
        final InputStream resourceAsStream = getClass()
                .getResourceAsStream(String.format("/%s", nameFile));
        return JSONValue.parse(new InputStreamReader(resourceAsStream, "UTF-8")).toString();
    }

}
