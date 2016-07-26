package com.baeldung.mvc.velocity.test;

import com.baeldung.mvc.velocity.domain.Tutorial;
import com.baeldung.mvc.velocity.service.ITutorialsService;
import com.baeldung.mvc.velocity.spring.config.WebConfig;
import com.baeldung.mvc.velocity.test.config.TestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(locations = {"classpath:mvc-servlet.xml"})
@ContextConfiguration(classes = { TestConfig.class, WebConfig.class }) @WebAppConfiguration public class DataContentControllerTest {

    private MockMvc mockMvc;

    @Autowired private ITutorialsService tutServiceMock;

    @Autowired private WebApplicationContext webApplicationContext;

    @Before public void setUp() {
        Mockito.reset(tutServiceMock);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test public void testModel() throws Exception {

        Mockito.when(tutServiceMock.listTutorials())
          .thenReturn(Arrays.asList(
            new Tutorial(1, "Guava", "Introduction to Guava", "GuavaAuthor"),
            new Tutorial(2, "Android", "Introduction to Android", "AndroidAuthor")));

        mockMvc.perform(get("/"))
          .andExpect(status().isOk()).andExpect(view().name("index"))
          .andExpect(model().attribute("tutorials", hasSize(2)))
          .andExpect(model()
            .attribute("tutorials",
              hasItem(allOf(hasProperty("tutId", is(1)),
              hasProperty("author", is("GuavaAuthor")),
              hasProperty("title", is("Guava"))))))
          .andExpect(model()
            .attribute("tutorials", hasItem(allOf(hasProperty("tutId", is(2)),
              hasProperty("author", is("AndroidAuthor")),
              hasProperty("title", is("Android"))))));

        mockMvc.perform(get("/"))
          .andExpect(xpath("//table").exists());
        mockMvc.perform(get("/"))
          .andExpect(xpath("//td[@id='tutId_1']").exists());
    }
}
