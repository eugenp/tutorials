
package com.baeldung.jaxws.server.topdown;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.baeldung.jaxws.server.topdown package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _CountEmployeesResponse_QNAME = new QName("http://topdown.server.jaxws.baeldung.com/", "countEmployeesResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.baeldung.jaxws.server.topdown
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://topdown.server.jaxws.baeldung.com/", name = "countEmployeesResponse")
    public JAXBElement<Integer> createCountEmployeesResponse(Integer value) {
        return new JAXBElement<Integer>(_CountEmployeesResponse_QNAME, Integer.class, null, value);
    }

}
