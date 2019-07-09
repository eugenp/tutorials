package com.baeldung.properties;

import com.baeldung.properties.beans.ConfigurationPropertiesRefreshConfigBean;
import com.baeldung.properties.beans.EnvironmentConfigBean;
import com.baeldung.properties.beans.PropertiesConfigBean;
import com.baeldung.properties.beans.ValueRefreshConfigBean;
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

    @Autowired WebApplicationContext webApplicationContext;

    @Autowired ValueRefreshConfigBean valueRefreshConfigBean;

    @Autowired ConfigurationPropertiesRefreshConfigBean configurationPropertiesRefreshConfigBean;

    @Autowired EnvironmentConfigBean environmentConfigBean;

    @Autowired PropertiesConfigBean propertiesConfigBean;

    @Autowired
    @Qualifier("singletonValueRefreshConfigBean")
    ValueRefreshConfigBean singletonValueRefreshConfigBean;

    long refreshDelay = 3000;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders
          .webAppContextSetup(webApplicationContext)
          .build();
        createConfig("extra.properties", "application.dynamic.prop1", "baeldung");
        createConfig("extra2.properties", "application.dynamic.prop2", "baeldung");
        Thread.sleep(refreshDelay);
        callRefresh();
    }

    @After
    public void tearDown() throws Exception {
        createConfig("extra.properties", "application.dynamic.prop1", "baeldung");
        createConfig("extra2.properties", "application.dynamic.prop2", "baeldung");
    }

    @Test
    public void givenEnvironmentComponent_whenConfigChanged_thenExpectChangeValue() throws Exception {
        Assert.assertEquals("baeldung", environmentConfigBean.getProperty1());

        createConfig("extra.properties", "application.dynamic.prop1", "baeldung1");
        Thread.sleep(refreshDelay);

        Assert.assertEquals("baeldung1", environmentConfigBean.getProperty1());
    }

    @Test
    public void givenEnvironmentComponent_whenConfig2Changed_thenExpectChangeValue() throws Exception {
        Assert.assertEquals("baeldung", environmentConfigBean.getProperty2());

        createConfig("extra2.properties", "application.dynamic.prop2", "baeldung1");
        Thread.sleep(refreshDelay);

        Assert.assertEquals("baeldung1", environmentConfigBean.getProperty2());
    }

    @Test
    public void givenPropertiesComponent_whenConfigChanged_thenExpectChangeValue() throws Exception {
        Assert.assertEquals("baeldung", propertiesConfigBean.getProperty1());

        createConfig("extra.properties", "application.dynamic.prop1", "baeldung1");
        Thread.sleep(refreshDelay);

        Assert.assertEquals("baeldung1", propertiesConfigBean.getProperty1());
    }

    @Test
    public void givenValueRefreshScopedBean_whenConfigChangedAndRefreshCalled_thenExpectChangeValue() throws Exception {
        Assert.assertEquals("baeldung", valueRefreshConfigBean.getProp1());

        createConfig("extra.properties", "application.dynamic.prop1", "baeldung1");
        Thread.sleep(refreshDelay);

        Assert.assertEquals("baeldung", valueRefreshConfigBean.getProp1());

        callRefresh();

        Assert.assertEquals("baeldung1", valueRefreshConfigBean.getProp1());
    }

    @Test
    public void givenSingletonValueRefreshScopedBean_whenConfigChangedAndRefreshCalled_thenExpectOldValue() throws Exception {

        Assert.assertEquals("baeldung", singletonValueRefreshConfigBean.getProp1());

        createConfig("extra.properties", "application.dynamic.prop1", "baeldung1");

        Thread.sleep(refreshDelay);

        Assert.assertEquals("baeldung", singletonValueRefreshConfigBean.getProp1());

        callRefresh();

        Assert.assertEquals("baeldung", singletonValueRefreshConfigBean.getProp1());
    }

    @Test
    public void givenConfigurationPropertiesRefreshScopedValueComponent_whenConfigChangedAndRefreshCalled_thenExpectChangeValue() throws Exception {

        Assert.assertEquals("baeldung", configurationPropertiesRefreshConfigBean.getProp1());

        createConfig("extra.properties", "application.dynamic.prop1", "baeldung1");
        Thread.sleep(refreshDelay);

        Assert.assertEquals("baeldung", configurationPropertiesRefreshConfigBean.getProp1());

        callRefresh();

        Assert.assertEquals("baeldung1", configurationPropertiesRefreshConfigBean.getProp1());
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
