package com.baeldung.xmlhtml.helpers.jaxb;

import com.baeldung.xmlhtml.pojo.jaxb.html.FullHTML;
import com.baeldung.xmlhtml.pojo.jaxb.html.SimpleHTML;
import com.baeldung.xmlhtml.pojo.jaxb.html.elements.Ancestor;
import com.baeldung.xmlhtml.pojo.jaxb.html.elements.Body;
import com.baeldung.xmlhtml.pojo.jaxb.html.elements.Descendant;
import com.baeldung.xmlhtml.pojo.jaxb.html.elements.Meta;
import com.baeldung.xmlhtml.pojo.jaxb.xml.XmlFull;
import com.baeldung.xmlhtml.pojo.jaxb.xml.XmlSimple;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.baeldung.xmlhtml.Constants.*;

/**
 * @author Adam In Tae Gerard
 * <p>
 * All JAXB logic in here :)
 */

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
        } catch (Exception ex) {
            System.out.println(EXCEPTION_ENCOUNTERED + ex);
        }

        return marshaller;
    }

    /**
     * This example demonstrates unmarshalling from an example XmlFull and marshalling
     * to a shortened but valid HTML5 document.
     */

    public static void simple() {

        try {

            /**
             *   Cast to desired POJO.
             */

            XmlSimple xml = (XmlSimple) getContextUnmarshaller(XmlSimple.class).unmarshal(new File(JAXB_SIMPLE_IN));
            print(xml.toString());
            print(xml.getAncestor().get(0).toString());

            /**
             * Transfer exchanged to writeable POJO.
             */

            SimpleHTML html = new SimpleHTML();
            List<Descendant> ancestor = new ArrayList<>();
            ancestor.add(xml.getAncestor().get(0));
            html.setAncestor(ancestor);

            /**
             * Marshall into XML (HTML).
             */

            getContextMarshaller(SimpleHTML.class).marshal(html, new File(JAXB_SIMPLE_OUT));

        } catch (Exception ex) {
            System.out.println(EXCEPTION_ENCOUNTERED + ex);
        }

    }

    /**
     * This example demonstrates unmarshalling from an example XmlFull and marshalling
     * to a full, semantically valid, HTML5 document.
     */

    public static void full() {

        try {

            /**
             *   Cast to desired POJO.
             */

            XmlFull xml = (XmlFull) getContextUnmarshaller(XmlFull.class).unmarshal(new File(JAXB_FULL_IN));
            print(xml.toString());
            print(xml.getAncestor().toString());
            print(xml.getAncestor().getDescendant().toString());
            print(xml.getCustomElement().toString());

            /**
             * Transfer exchanged to writeable POJO.
             */

            FullHTML html = new FullHTML();

            Meta meta = new Meta();
            meta.setTitle("example");
            html.getHead().add(meta);

            Body body = new Body();
            body.setAncestor(xml.getAncestor());
            body.setCustomElement(xml.getCustomElement());
            body.setTitle("example");

            html.setBody(body);

            /**
             * Marshall into XML (HTML).
             */

            getContextMarshaller(FullHTML.class).marshal(html, new File(JAXB_FULL_OUT));

        } catch (Exception ex) {
            System.out.println(EXCEPTION_ENCOUNTERED + ex);
        }


    }

}