package com.baeldung.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.baeldung.validation.listvalidation.SpringListValidationApplication;

/**
 * This is the test class for {@link com.baeldung.controller.controller.PassParametersController} class.
 * 09/09/2017
 *
 * @author Ahmet Cetin
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringListValidationApplication.class)
public class PassParametersControllerIntegrationTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testPassParametersWithModel() throws Exception {
        ModelAndView mv = this.mockMvc.perform(MockMvcRequestBuilders.get("/showViewPage")).andReturn().getModelAndView();

        //Validate view
        Assert.assertEquals(mv.getViewName(), "view/viewPage");

        //Validate attribute
        Assert.assertEquals(mv.getModelMap().get("message").toString(), "Baeldung");
    }

    @Test
    public void testPassParametersWithModelMap() throws Exception {
        ModelAndView mv = this.mockMvc.perform(MockMvcRequestBuilders.get("/printViewPage")).andReturn().getModelAndView();

        //Validate view
        Assert.assertEquals(mv.getViewName(), "view/viewPage");

        //Validate attribute
        Assert.assertEquals(mv.getModelMap().get("message").toString(), "Baeldung");
    }

    @Test
    public void testPassParametersWithModelAndView() throws Exception {
        ModelAndView mv = this.mockMvc.perform(MockMvcRequestBuilders.get("/goToViewPage")).andReturn().getModelAndView();

        //Validate view
        Assert.assertEquals(mv.getViewName(), "view/viewPage");

        //Validate attribute
        Assert.assertEquals(mv.getModelMap().get("message").toString(), "Baeldung");
    }
}
