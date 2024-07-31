package com.baeldung.thymeleaf.mvcdata;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.baeldung.thymeleaf.mvcdata.repository.EmailData;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
public class EmailControllerUnitTest {

    EmailData emailData = new EmailData();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenCallModelAttributes_thenReturnEmailData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/email/modelattributes"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("You have received a new message")));
    }

    @Test
    public void whenCallRequestParameters_thenReturnEmailData() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("emailsubject", emailData.getEmailSubject());
        params.add("emailcontent", emailData.getEmailBody());
        params.add("emailaddress", emailData.getEmailAddress1());
        params.add("emailaddress", emailData.getEmailAddress2());
        params.add("emaillocale", emailData.getEmailLocale());
        mockMvc.perform(MockMvcRequestBuilders.get("/email/requestparameters")
                .params(params))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("en-US")));
    }

    @Test
    public void whenCallBeanData_thenReturnEmailData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/email/beandata"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("jhon.doe@example.com")));
    }

}
