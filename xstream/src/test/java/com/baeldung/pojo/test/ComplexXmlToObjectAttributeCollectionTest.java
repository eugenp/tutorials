package com.baeldung.pojo.test;

import com.baeldung.complex.pojo.ContactDetails;
import com.baeldung.complex.pojo.Customer;
import com.baeldung.initializer.SimpleXstreamInitializer;
import com.thoughtworks.xstream.XStream;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;

public class ComplexXmlToObjectAttributeCollectionTest {

    private XStream xstream = null;

    @Before
    public void dataSetup() {
        SimpleXstreamInitializer simpleXstreamInitializer = new SimpleXstreamInitializer();
        xstream = simpleXstreamInitializer.getXstreamInstance();
        xstream.processAnnotations(Customer.class);
    }

    @Test
    public void convertXmlToObjectFromFile() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            FileReader reader = new FileReader(classLoader.getResource("data-file-alias-field-complex.xml").getFile());
            Customer customer = (Customer) xstream.fromXML(reader);
            Assert.assertNotNull(customer);
            Assert.assertNotNull(customer.getContactDetailsList());
            for (ContactDetails contactDetails : customer.getContactDetailsList()) {
                Assert.assertNotNull(contactDetails.getContactType());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
