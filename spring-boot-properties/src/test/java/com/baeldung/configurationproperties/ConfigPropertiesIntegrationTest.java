package com.baeldung.configurationproperties;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.configurationproperties.ConfigProperties;
import com.baeldung.properties.AdditionalProperties;
import com.baeldung.properties.ConfigPropertiesDemoApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConfigPropertiesDemoApplication.class)
@TestPropertySource("classpath:configprops-test.properties")
public class ConfigPropertiesIntegrationTest {

    @Autowired
    private ConfigProperties properties;

    @Autowired
    private AdditionalProperties additionalProperties;

    @Test
    public void whenSimplePropertyQueriedthenReturnsProperty() throws Exception {
        Assert.assertTrue("From address is read as null!", properties.getFrom() != null);
    }

    @Test
    public void whenListPropertyQueriedthenReturnsProperty() throws Exception {
        Assert.assertTrue("Couldn't bind list property!", properties.getDefaultRecipients().size() == 2);
        Assert.assertTrue("Incorrectly bound list property. Expected 2 entries!", properties.getDefaultRecipients().size() == 2);
    }

    @Test
    public void whenMapPropertyQueriedthenReturnsProperty() throws Exception {
        Assert.assertTrue("Couldn't bind map property!", properties.getAdditionalHeaders() != null);
        Assert.assertTrue("Incorrectly bound map property. Expected 3 Entries!", properties.getAdditionalHeaders().size() == 3);
    }

    @Test
    public void whenObjectPropertyQueriedthenReturnsProperty() throws Exception {
        Assert.assertTrue("Couldn't bind map property!", properties.getCredentials() != null);
        Assert.assertTrue("Incorrectly bound object property!", properties.getCredentials().getAuthMethod().equals("SHA1"));
        Assert.assertTrue("Incorrectly bound object property!", properties.getCredentials().getUsername().equals("john"));
        Assert.assertTrue("Incorrectly bound object property!", properties.getCredentials().getPassword().equals("password"));
    }

    @Test
    public void whenAdditionalPropertyQueriedthenReturnsProperty() {
        Assert.assertTrue(additionalProperties.getUnit().equals("km"));
        Assert.assertTrue(additionalProperties.getMax() == 100);
    }
}
