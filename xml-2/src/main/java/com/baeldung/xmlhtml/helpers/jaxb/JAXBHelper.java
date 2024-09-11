package com.baeldung.xmlhtml.helpers.jaxb;

import com.baeldung.xmlhtml.pojo.jaxb.html.ExampleHTML;
import com.baeldung.xmlhtml.pojo.jaxb.html.elements.Body;
import com.baeldung.xmlhtml.pojo.jaxb.html.elements.CustomElement;
import com.baeldung.xmlhtml.pojo.jaxb.html.elements.Meta;
import com.baeldung.xmlhtml.pojo.jaxb.html.elements.NestedElement;
import com.baeldung.xmlhtml.pojo.jaxb.xml.XMLExample;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;

import static com.baeldung.xmlhtml.Constants.*;

public class JAXBHelper {

    private static void print(String xmlContent) {
        System.out.println(xmlContent);
    }

    private static Unmarshaller getContextUnmarshaller(Class clazz) {
        Unmarshaller unmarshaller = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            unmarshaller = context.createUnmarshaller();
        } catch (Exception ex) {
            System.out.println(EXCEPTION_ENCOUNTERED + ex);
        }
        return unmarshaller;
    }

    private static Marshaller getContextMarshaller(Class clazz) {
        Marshaller marshaller = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty("jaxb.fragment", Boolean.TRUE);
        } catch (Exception ex) {
            System.out.println(EXCEPTION_ENCOUNTERED + ex);
        }
        return marshaller;
    }

    public static void example() {
        try {
            XMLExample xml = (XMLExample) JAXBHelper.getContextUnmarshaller(XMLExample.class).unmarshal(new File(XML_FILE_IN));
            JAXBHelper.print(xml.toString());
            ExampleHTML html = new ExampleHTML();

            Body body = new Body();
            CustomElement customElement = new CustomElement();
            NestedElement nested = new NestedElement();
            CustomElement child = new CustomElement();

            customElement.setValue("descendantOne: " + xml.getAncestor().getDescendantOne().getValue());
            child.setValue("descendantThree: " + xml.getAncestor().getDescendantTwo().getDescendantThree().getValue());
            nested.setCustomElement(child);

            body.setCustomElement(customElement);
            body.setNestedElement(nested);

            Meta meta = new Meta();
            meta.setTitle("example");
            html.getHead().add(meta);
            html.setBody(body);

            JAXBHelper.getContextMarshaller(ExampleHTML.class).marshal(html, new File(JAXB_FILE_OUT));

        } catch (Exception ex) {
            System.out.println(EXCEPTION_ENCOUNTERED + ex);
        }
    }
}