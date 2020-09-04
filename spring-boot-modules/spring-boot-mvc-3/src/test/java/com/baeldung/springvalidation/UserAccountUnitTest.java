package com.baeldung.springvalidation;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserAccountUnitTest{

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenSaveBasicInfo_whenCorrectInput_thenSuccess() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/saveBasicInfo")
            .accept(MediaType.TEXT_HTML)
            .param("name", "test123")
            .param("password", "pass"))
            .andExpect(view().name("success"))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    public void givenSaveBasicInfo_whenIncorrectInput_thenError() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/saveBasicInfo")
            .accept(MediaType.TEXT_HTML))
            // .param("name", "test123")
            // .param("password", "pass"))
            .andExpect(model().errorCount(2))
            .andExpect(view().name("error"))
            .andExpect(status().isOk())
            .andDo(print());
    }

    /*  @Test 
    public void givenSaveBasicInfoWithAddress_thenError() throws Exception {
    
        UserAccount useraccount = new UserAccount();
        //useraccount.setName("name");
        //useraccount.setPassword("pass");
        UserAddress uadd = new UserAddress();
        uadd.setCountryCode("UK");
       // useraccount.setUseraddress(uadd);
    
        this.mockMvc.perform(MockMvcRequestBuilders.post("/saveBasicInfo")
           // .flashAttr("useraccount", useraccount)
            .accept(MediaType.TEXT_HTML))
            // .param("name", "test123")
            // .param("password", "pass"))
    
            .andExpect(model().errorCount(2))
            .andExpect(view().name("error"))
            // .andExpect(model().attribute("message", "Valid form"))
            .andExpect(status().isOk())
            // .andExpect(model().attribute("useraccount", contains(org.hamcrest.object.HasToString.hasToString(uadd.toString()))));
            .andDo(print());
    }*/

}
