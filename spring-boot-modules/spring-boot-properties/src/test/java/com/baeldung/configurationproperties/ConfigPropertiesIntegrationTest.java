package com.baeldung.configurationproperties;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.properties.AdditionalProperties;
import com.baeldung.properties.ConfigPropertiesDemoApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ConfigPropertiesDemoApplication.class})
@TestPropertySource(locations = {"classpath:configprops-test.properties"})
public class ConfigPropertiesIntegrationTest {

    @Autowired
    private ConfigProperties configProperties;

    @Autowired
    private AdditionalProperties additionalProperties;

    @Test
    public void whenSimplePropertyQueriedthenReturnsProperty() throws Exception {
        Assert.assertTrue("From address is read as null!", configProperties.getFrom() != null);
    }

    @Test
    public void whenListPropertyQueriedthenReturnsProperty() throws Exception {
        Assert.assertTrue("Couldn't bind list property!", configProperties.getDefaultRecipients().size() == 2);
        Assert.assertTrue("Incorrectly bound list property. Expected 2 entries!", configProperties.getDefaultRecipients().size() == 2);
    }

    @Test
    public void whenMapPropertyQueriedthenReturnsProperty() throws Exception {
        Assert.assertTrue("Couldn't bind map property!", configProperties.getAdditionalHeaders() != null);
        Assert.assertTrue("Incorrectly bound map property. Expected 3 Entries!", configProperties.getAdditionalHeaders().size() == 3);
    }

    @Test
    public void whenObjectPropertyQueriedthenReturnsProperty() throws Exception {
        Assert.assertTrue("Couldn't bind map property!", configProperties.getCredentials() != null);
        Assert.assertTrue("Incorrectly bound object property!", configProperties.getCredentials().getAuthMethod().equals("SHA1"));
        Assert.assertTrue("Incorrectly bound object property!", configProperties.getCredentials().getUsername().equals("john"));
        Assert.assertTrue("Incorrectly bound object property!", configProperties.getCredentials().getPassword().equals("password"));
    }

    @Test
    public void whenAdditionalPropertyQueriedthenReturnsProperty() {
        Assert.assertTrue(additionalProperties.getUnit().equals("km"));
        Assert.assertTrue(additionalProperties.getMax() == 100);
    }

}
