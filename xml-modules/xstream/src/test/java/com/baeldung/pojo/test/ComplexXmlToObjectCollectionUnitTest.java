package com.baeldung.pojo.test;

import com.baeldung.implicit.collection.pojo.Customer;
import com.baeldung.initializer.SimpleXstreamInitializer;
import com.thoughtworks.xstream.XStream;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class ComplexXmlToObjectCollectionUnitTest {

    private XStream xstream = null;

    @Before
    public void dataSetup() {
        SimpleXstreamInitializer simpleXstreamInitializer = new SimpleXstreamInitializer();
        xstream = simpleXstreamInitializer.getXstreamInstance();
        xstream.processAnnotations(Customer.class);
    }

    @Test
    public void convertXmlToObjectFromFile() throws FileNotFoundException {
        ClassLoader classLoader = getClass().getClassLoader();
        FileReader reader = new FileReader(classLoader
          .getResource("data-file-alias-implicit-collection.xml")
          .getFile());
        Customer customer = (Customer) xstream.fromXML(reader);
        Assert.assertNotNull(customer);
        Assert.assertNotNull(customer.getContactDetailsList());
    }

}
