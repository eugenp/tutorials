
package com.baeldung;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.baeldung package. 
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

    private final static QName _ShowPersonDetailResponse_QNAME = new QName("http://baeldung.com/", "showPersonDetailResponse");
    private final static QName _ShowPersonDetail_QNAME = new QName("http://baeldung.com/", "showPersonDetail");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.baeldung
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ShowPersonDetail }
     * 
     */
    public ShowPersonDetail createShowPersonDetail() {
        return new ShowPersonDetail();
    }

    /**
     * Create an instance of {@link ShowPersonDetailResponse }
     * 
     */
    public ShowPersonDetailResponse createShowPersonDetailResponse() {
        return new ShowPersonDetailResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ShowPersonDetailResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://baeldung.com/", name = "showPersonDetailResponse")
    public JAXBElement<ShowPersonDetailResponse> createShowPersonDetailResponse(ShowPersonDetailResponse value) {
        return new JAXBElement<ShowPersonDetailResponse>(_ShowPersonDetailResponse_QNAME, ShowPersonDetailResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ShowPersonDetail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://baeldung.com/", name = "showPersonDetail")
    public JAXBElement<ShowPersonDetail> createShowPersonDetail(ShowPersonDetail value) {
        return new JAXBElement<ShowPersonDetail>(_ShowPersonDetail_QNAME, ShowPersonDetail.class, null, value);
    }

}
