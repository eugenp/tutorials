package com.spaceprogram.bigcache;

import com.spaceprogram.bigcache.marshallers.JAXBMarshaller;
import org.junit.Test;

import java.util.Date;

/**
 * User: treeder
 * Date: Oct 18, 2008
 * Time: 2:35:25 PM
 */
public class JaxbMarshallingTests {
    @Test
    public void testObjectMarshallingNoAnnotations() throws Exception {
        String key = "x";
        RootObjectNoAnnotations s = new RootObjectNoAnnotations();
        s.setId("This is my data for the cache.");
        s.setCreated(new Date());
        s.setSomeObject(new SomeObject());
        JAXBMarshaller marshaller = new JAXBMarshaller();
        byte[] bytes = marshaller.marshal(s);
        String out = new String(bytes);
        System.out.println(out);
//        Assert.assertEquals(s, ret);
    }
}
