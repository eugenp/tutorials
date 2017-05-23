package org.baeldung.bean.injectiontypes;

import org.baeldung.bean.injectiontypes.models.DataProviderInterface;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BeanInjectionConfig.class})
public class BeanInjectionTypesTest {

    final Logger logger = LoggerFactory.getLogger(BeanInjectionTypesTest.class);

    @Autowired
    private DataProviderInterface dataProviderInterface;

    /**
     * Tests message.
     */
    @Test
    public void testMessage() {
        assertNotNull("Constructor message instance is null.", dataProviderInterface);

        String msg = dataProviderInterface.getData();

        assertNotNull("Message is null.", msg);

        String expectedMessage = "Bean injection is fun";

        assertEquals("Message should be '" + expectedMessage + "'.", expectedMessage, msg);

        logger.info("message='{}'", msg);
    }

}