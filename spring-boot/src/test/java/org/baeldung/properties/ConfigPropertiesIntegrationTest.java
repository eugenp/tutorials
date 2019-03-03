package org.baeldung.properties;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConfigPropertiesDemoApplication.class)
@TestPropertySource("classpath:configprops-test.properties")
public class ConfigPropertiesIntegrationTest {

    @Autowired
    private ConfigProperties properties;

    @Test
    public void whenSimplePropertyQueriedthenReturnsProperty() throws Exception {
        Assert.assertEquals("Incorrectly bound hostName property", "host@mail.com", properties.getHostName());
        Assert.assertEquals("Incorrectly bound port property", 9000, properties.getPort());
        Assert.assertEquals("Incorrectly bound from property", "mailer@mail.com", properties.getFrom());
    }

    @Test
    public void whenListPropertyQueriedthenReturnsProperty() throws Exception {
        List<String> defaultRecipients = properties.getDefaultRecipients();
        Assert.assertTrue("Couldn't bind list property!", defaultRecipients.size() == 2);
        Assert.assertTrue("Incorrectly bound list property. Expected 2 entries!", defaultRecipients.size() == 2);
        Assert.assertEquals("Incorrectly bound list[0] property", "admin@mail.com", defaultRecipients.get(0));
        Assert.assertEquals("Incorrectly bound list[1] property", "owner@mail.com", defaultRecipients.get(1));
    }

    @Test
    public void whenMapPropertyQueriedthenReturnsProperty() throws Exception {
        Map<String, String> additionalHeaders = properties.getAdditionalHeaders();
        Assert.assertTrue("Couldn't bind map property!", additionalHeaders != null);
        Assert.assertTrue("Incorrectly bound map property. Expected 3 Entries!", additionalHeaders.size() == 3);
        Assert.assertEquals("Incorrectly bound map[redelivery] property", "true", additionalHeaders.get("redelivery"));
        Assert.assertEquals("Incorrectly bound map[secure] property", "true", additionalHeaders.get("secure"));
        Assert.assertEquals("Incorrectly bound map[p3] property", "value", additionalHeaders.get("p3"));
    }

    @Test
    public void whenObjectPropertyQueriedthenReturnsProperty() throws Exception {
        Credentials credentials = properties.getCredentials();
        Assert.assertTrue("Couldn't bind map property!", credentials != null);
        Assert.assertEquals("Incorrectly bound object property, authMethod", "SHA1", credentials.getAuthMethod());
        Assert.assertEquals("Incorrectly bound object property, username", "john", credentials.getUsername());
        Assert.assertEquals("Incorrectly bound object property, password", "password", credentials.getPassword());
    }
}
