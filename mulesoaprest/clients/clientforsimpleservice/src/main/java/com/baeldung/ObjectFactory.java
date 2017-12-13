
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
    private final static QName _AddNumbers_QNAME = new QName("http://baeldung.com/", "addNumbers");
    private final static QName _AddNumbersResponse_QNAME = new QName("http://baeldung.com/", "addNumbersResponse");
    private final static QName _AddNumbersArg1_QNAME = new QName("http://baeldung.com/", "arg1");
    private final static QName _AddNumbersArg0_QNAME = new QName("http://baeldung.com/", "arg0");
    private final static QName _AddNumbersResponseReturn_QNAME = new QName("http://baeldung.com/", "return");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.baeldung
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AddNumbers }
     * 
     */
    public AddNumbers createAddNumbers() {
        return new AddNumbers();
    }

    /**
     * Create an instance of {@link AddNumbersResponse }
     * 
     */
    public AddNumbersResponse createAddNumbersResponse() {
        return new AddNumbersResponse();
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

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddNumbers }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://baeldung.com/", name = "addNumbers")
    public JAXBElement<AddNumbers> createAddNumbers(AddNumbers value) {
        return new JAXBElement<AddNumbers>(_AddNumbers_QNAME, AddNumbers.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddNumbersResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://baeldung.com/", name = "addNumbersResponse")
    public JAXBElement<AddNumbersResponse> createAddNumbersResponse(AddNumbersResponse value) {
        return new JAXBElement<AddNumbersResponse>(_AddNumbersResponse_QNAME, AddNumbersResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://baeldung.com/", name = "arg1", scope = AddNumbers.class)
    public JAXBElement<Integer> createAddNumbersArg1(Integer value) {
        return new JAXBElement<Integer>(_AddNumbersArg1_QNAME, Integer.class, AddNumbers.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://baeldung.com/", name = "arg0", scope = AddNumbers.class)
    public JAXBElement<Integer> createAddNumbersArg0(Integer value) {
        return new JAXBElement<Integer>(_AddNumbersArg0_QNAME, Integer.class, AddNumbers.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://baeldung.com/", name = "return", scope = AddNumbersResponse.class)
    public JAXBElement<String> createAddNumbersResponseReturn(String value) {
        return new JAXBElement<String>(_AddNumbersResponseReturn_QNAME, String.class, AddNumbersResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://baeldung.com/", name = "return", scope = ShowPersonDetailResponse.class)
    public JAXBElement<String> createShowPersonDetailResponseReturn(String value) {
        return new JAXBElement<String>(_AddNumbersResponseReturn_QNAME, String.class, ShowPersonDetailResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://baeldung.com/", name = "arg1", scope = ShowPersonDetail.class)
    public JAXBElement<Integer> createShowPersonDetailArg1(Integer value) {
        return new JAXBElement<Integer>(_AddNumbersArg1_QNAME, Integer.class, ShowPersonDetail.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://baeldung.com/", name = "arg0", scope = ShowPersonDetail.class)
    public JAXBElement<String> createShowPersonDetailArg0(String value) {
        return new JAXBElement<String>(_AddNumbersArg0_QNAME, String.class, ShowPersonDetail.class, value);
    }

}
