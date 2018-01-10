package com.baeldung.beans;

import com.baeldung.beans.model.Info;
import com.baeldung.beans.service.MyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MyService myService;

    @Test
    public void successTest() throws Exception {

        when(myService.getVersion()).thenReturn(new Info(
                "",
                "",
                "https://support.run.pivotal.io",
                "0",
                "Cloud Foundry sponsored by Pivotal",
                "https://login.run.pivotal.io",
                "https://uaa.run.pivotal.io",
                "6.22.0",
                "latest",
                "2.101.0",
                "ssh.run.pivotal.io:2222",
                "e7:13:4e:32:ee:39:62:df:54:41:d7:f7:8b:b2:a7:6b",
                "ssh-proxy",
                "wss://doppler.run.pivotal.io:443",
                "https://api.run.pivotal.io/routing"));

        this.mockMvc.perform(get("/api/info"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"version\":\"2.101.0\"}")));

        then(myService).should(times(1)).getVersion();
    }

}
