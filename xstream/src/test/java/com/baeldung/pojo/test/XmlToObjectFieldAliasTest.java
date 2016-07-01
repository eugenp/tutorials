package com.baeldung.pojo.test;

import com.baeldung.initializer.SimpleXstreamInitializer;
import com.baeldung.pojo.Customer;
import com.thoughtworks.xstream.XStream;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;

public class XmlToObjectFieldAliasTest {

    private XStream xstream = null;

    @Before
    public void dataSetup() {
        SimpleXstreamInitializer simpleXstreamInitializer = new SimpleXstreamInitializer();
        xstream = simpleXstreamInitializer.getXstreamInstance();
        xstream.alias("customer", Customer.class);
        xstream.aliasField("fn", Customer.class, "firstName");
    }

    @Test
    public void convertXmlToObjectFromFile() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            FileReader reader = new FileReader(classLoader.getResource("data-file-alias-field.xml").getFile());
            Customer customer = (Customer) xstream.fromXML(reader);
            Assert.assertNotNull(customer);
            Assert.assertNotNull(customer.getFirstName());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
