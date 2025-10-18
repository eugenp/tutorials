
package com.baeldung.jaxb.gen;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.baeldung.jaxb.gen package. 
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

    private final static QName _UserRequest_QNAME = new QName("http://www.baeldung.com/jaxb/gen", "userRequest");
    private final static QName _UserResponse_QNAME = new QName("http://www.baeldung.com/jaxb/gen", "userResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.baeldung.jaxb.gen
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UserRequest }
     * 
     */
    public UserRequest createUserRequest() {
        return new UserRequest();
    }

    /**
     * Create an instance of {@link UserResponse }
     * 
     */
    public UserResponse createUserResponse() {
        return new UserResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserRequest }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link UserRequest }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.baeldung.com/jaxb/gen", name = "userRequest")
    public JAXBElement<UserRequest> createUserRequest(UserRequest value) {
        return new JAXBElement<UserRequest>(_UserRequest_QNAME, UserRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link UserResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://www.baeldung.com/jaxb/gen", name = "userResponse")
    public JAXBElement<UserResponse> createUserResponse(UserResponse value) {
        return new JAXBElement<UserResponse>(_UserResponse_QNAME, UserResponse.class, null, value);
    }

}
