package com.baeldung.springvalidation;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.baeldung.springvalidation.controller.UserAccountController;
import com.baeldung.springvalidation.domain.UserAddress;

public class UserAccountValidationTest {
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new UserAccountController())
            .build();
    }

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

        UserAddress uadd = new UserAddress();
        uadd.setCountryCode("UK");

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
