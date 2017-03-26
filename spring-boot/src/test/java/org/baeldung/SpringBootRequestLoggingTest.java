package org.baeldung;

import java.nio.charset.Charset;

import org.baeldung.domain.UserVO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class SpringBootRequestLoggingTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setupMockMvc() {
        MockMvcBuilders.webAppContextSetup(webApplicationContext);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilter(webApplicationContext.getBean(CommonsRequestLoggingFilter.class)).build();

    }

    @Test
    public void givenRequestHasBeenMade_whenGetMeetsAllOfGivenConditions_thenCorrect() throws Exception {
        MediaType contentType = new MediaType(MediaType.APPLICATION_FORM_URLENCODED.getType(), MediaType.APPLICATION_FORM_URLENCODED.getSubtype(), Charset.forName("utf8"));

        mockMvc.perform(MockMvcRequestBuilders.post("/helloGet?home=Houston").contentType(contentType)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void givenRequestHasBeenMade_whenPostMeetsAllOfGivenConditions_thenCorrect() throws Exception {
        MediaType contentType = new MediaType(MediaType.APPLICATION_FORM_URLENCODED.getType(), MediaType.APPLICATION_FORM_URLENCODED.getSubtype(), Charset.forName("utf8"));

        mockMvc.perform(MockMvcRequestBuilders.post("/helloPost").param("name", "suse").contentType(contentType)).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void givenRequestHasBeenMade_whenJsonPostMeetsAllOfGivenConditions_thenCorrect() throws Exception {
        MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
        UserVO user = new UserVO();
        user.setName("testUser");
        user.setPwd("testPwd");
        Gson gson = new Gson();
        String json = gson.toJson(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/aJsonPostMethod").content(json).contentType(contentType)).andExpect(MockMvcResultMatchers.status().isOk());
    }

}
