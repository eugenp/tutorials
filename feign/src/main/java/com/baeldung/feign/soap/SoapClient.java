package com.baeldung.feign.soap;

import feign.Headers;
import feign.RequestLine;

public interface SoapClient {
    @RequestLine("POST")
    @Headers({"SOAPAction: createUser", "Content-Type: text/xml;charset=UTF-8", "Accept: text/xml"})
    String createUserWithPlainText(String soapBody);

    @RequestLine("POST")
    @Headers({"Content-Type: text/xml;charset=UTF-8"})
    CreateUserResponse createUserWithSoap(CreateUserRequest soapBody);
}
