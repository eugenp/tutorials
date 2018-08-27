package org.baeldung.properties;

import java.util.Arrays;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ConfigPropertiesDemoApplication.class, initializers = JsonPropertyContextInitializer.class)
public class JsonPropertiesIntegrationTest {

    @Autowired
    private JsonProperties jsonProperties;

    @Autowired
    private CustomJsonProperties customJsonProperties;

    @Test
    public void givenJsonPropertySource_whenPropertySourceFactoryUsed_thenLoadFlatValues() {
        Assert.assertEquals("mailer@mail.com", jsonProperties.getHost());
        Assert.assertEquals(9090, jsonProperties.getPort());
        Assert.assertTrue(jsonProperties.isResend());
    }

    @Test
    public void givenJsonPropertySource_whenPropertySourceFactoryUsed_thenLoadListValues() {
        Assert.assertThat(jsonProperties.getTopics(), Matchers.is(Arrays.asList("spring", "boot")));
    }

    @Test
    public void givenJsonPropertySource_whenPropertySourceFactoryUsed_thenNestedValuesLoadedAsMap() {
        Assert.assertEquals("sender", jsonProperties.getSender()
            .get("name"));
        Assert.assertEquals("street", jsonProperties.getSender()
            .get("address"));
    }

    @Test
    public void givenCustomJsonPropertySource_whenLoadedIntoEnvironment_thenFlatValuesPopulated() {
        Assert.assertEquals("mailer.custom@mail.com", customJsonProperties.getHost());
        Assert.assertEquals(8081, customJsonProperties.getPort());
        Assert.assertTrue(customJsonProperties.isResend());
    }

    @Test
    public void givenCustomJsonPropertySource_whenLoadedIntoEnvironment_thenValuesLoadedIntoClassObject() {
        Assert.assertNotNull(customJsonProperties.getSender());
        Assert.assertEquals("sender.custom", customJsonProperties.getSender()
            .getName());
        Assert.assertEquals("street.custom", customJsonProperties.getSender()
            .getAddress());
    }

}
