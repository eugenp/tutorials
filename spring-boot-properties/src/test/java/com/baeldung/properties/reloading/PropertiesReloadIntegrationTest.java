package com.baeldung.properties.reloading;

import com.baeldung.properties.reloading.beans.ConfigurationPropertiesRefreshConfigBean;
import com.baeldung.properties.reloading.beans.EnvironmentConfigBean;
import com.baeldung.properties.reloading.beans.PropertiesConfigBean;
import com.baeldung.properties.reloading.beans.ValueRefreshConfigBean;
import java.io.FileOutputStream;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBootPropertiesTestApplication.class)
public class PropertiesReloadIntegrationTest {

    protected MockMvc mvc;

    protected long refreshDelay = 3000;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    ValueRefreshConfigBean valueRefreshConfigBean;

    @Autowired
    ConfigurationPropertiesRefreshConfigBean configurationPropertiesRefreshConfigBean;

    @Autowired
    EnvironmentConfigBean environmentConfigBean;

    @Autowired
    PropertiesConfigBean propertiesConfigBean;

    @Autowired
    @Qualifier("singletonValueRefreshConfigBean")
    ValueRefreshConfigBean singletonValueRefreshConfigBean;


    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders
          .webAppContextSetup(webApplicationContext)
          .build();
        createConfig("extra.properties", "application.theme.color", "blue");
        createConfig("extra2.properties", "application.theme.background", "red");
        Thread.sleep(refreshDelay);
        callRefresh();
    }

    @After
    public void tearDown() throws Exception {
        createConfig("extra.properties", "application.theme.color", "blue");
        createConfig("extra2.properties", "application.theme.background", "red");
    }

    @Test
    public void givenEnvironmentReader_whenColorChanged_thenExpectChangeValue() throws Exception {
        Assert.assertEquals("blue", environmentConfigBean.getColor());

        createConfig("extra.properties", "application.theme.color", "red");
        Thread.sleep(refreshDelay);

        Assert.assertEquals("red", environmentConfigBean.getColor());
    }

    @Test
    public void givenEnvironmentReader_whenBackgroundChanged_thenExpectChangeValue() throws Exception {
        Assert.assertEquals("red", environmentConfigBean.getBackgroundColor());

        createConfig("extra2.properties", "application.theme.background", "blue");
        Thread.sleep(refreshDelay);

        Assert.assertEquals("blue", environmentConfigBean.getBackgroundColor());
    }

    @Test
    public void givenPropertiesReader_whenColorChanged_thenExpectChangeValue() throws Exception {
        Assert.assertEquals("blue", propertiesConfigBean.getColor());

        createConfig("extra.properties", "application.theme.color", "red");
        Thread.sleep(refreshDelay);

        Assert.assertEquals("red", propertiesConfigBean.getColor());
    }

    @Test
    public void givenRefreshScopedValueReader_whenColorChangedAndRefreshCalled_thenExpectChangeValue() throws Exception {
        Assert.assertEquals("blue", valueRefreshConfigBean.getColor());

        createConfig("extra.properties", "application.theme.color", "red");
        Thread.sleep(refreshDelay);

        Assert.assertEquals("blue", valueRefreshConfigBean.getColor());

        callRefresh();

        Assert.assertEquals("red", valueRefreshConfigBean.getColor());
    }

    @Test
    public void givenSingletonRefreshScopedValueReader_whenColorChangedAndRefreshCalled_thenExpectOldValue() throws Exception {

        Assert.assertEquals("blue", singletonValueRefreshConfigBean.getColor());

        createConfig("extra.properties", "application.theme.color", "red");
        Thread.sleep(refreshDelay);

        Assert.assertEquals("blue", singletonValueRefreshConfigBean.getColor());

        callRefresh();

        Assert.assertEquals("blue", singletonValueRefreshConfigBean.getColor());
    }

    @Test
    public void givenRefreshScopedConfigurationPropertiesReader_whenColorChangedAndRefreshCalled_thenExpectChangeValue() throws Exception {

        Assert.assertEquals("blue", configurationPropertiesRefreshConfigBean.getColor());

        createConfig("extra.properties", "application.theme.color", "red");
        Thread.sleep(refreshDelay);

        Assert.assertEquals("blue", configurationPropertiesRefreshConfigBean.getColor());

        callRefresh();

        Assert.assertEquals("red", configurationPropertiesRefreshConfigBean.getColor());
    }

    public void callRefresh() throws Exception {
        MvcResult mvcResult = mvc
          .perform(MockMvcRequestBuilders
            .post("/actuator/refresh")
            .accept(MediaType.APPLICATION_JSON_VALUE))
          .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        Assert.assertEquals(response.getStatus(), 200);
    }

    public void createConfig(String file, String key, String value) throws Exception {
        FileOutputStream fo = new FileOutputStream(file);
        fo.write(String
          .format("%s=%s", key, value)
          .getBytes());
        fo.close();
    }
}
