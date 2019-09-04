package com.baeldung.xmlhtml.helpers;


import com.baeldung.xmlhtml.helpers.jaxb.JAXBHelper;
import com.baeldung.xmlhtml.helpers.stax.STAXHelper;

public class XMLRunner {

    public static void doWork() {
        JAXBHelper.example();
        STAXHelper.write(STAXHelper.read());
    }
}
