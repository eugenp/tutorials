package com.baeldung.feign.soap;

import feign.Feign;
import feign.Logger;
import feign.hc5.ApacheHttp5Client;
import feign.jaxb.JAXBContextFactory;
import feign.slf4j.Slf4jLogger;
import feign.soap.SOAPDecoder;
import feign.soap.SOAPEncoder;
import feign.soap.SOAPErrorDecoder;
import org.junit.jupiter.api.Test;

import javax.xml.ws.soap.SOAPFaultException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class FeignLiveTest {
    @Test
    void givenSOAPPayload_whenStringRequest_thenReturnSOAPResponse() {
        //@formatter:off
        String successMessage="Success! Created the user with id";
        SoapClient client = Feign.builder()
          .client(new ApacheHttp5Client())
          .logger(new Slf4jLogger(SoapClient.class))
          .logLevel(Logger.Level.FULL)
          .target(SoapClient.class, "http://localhost:18080/ws/users/");

        assertDoesNotThrow(() -> client.createUserWithPlainText(soapPayload()));

        String soapResponse= client.createUserWithPlainText(soapPayload());

        assertNotNull(soapResponse);
        assertTrue(soapResponse.contains(successMessage));
        //@formatter:on
    }

    @Test
    void whenSoapRequest_thenReturnSoapResponse() {
        JAXBContextFactory jaxbFactory = new JAXBContextFactory.Builder().withMarshallerJAXBEncoding("UTF-8").build();
        SoapClient client = Feign.builder()
          .encoder(new SOAPEncoder(jaxbFactory))
          .errorDecoder(new SOAPErrorDecoder())
          .logger(new Slf4jLogger())
          .logLevel(Logger.Level.FULL)
          .decoder(new SOAPDecoder(jaxbFactory))
          .target(SoapClient.class, "http://localhost:18080/ws/users/");
        CreateUserRequest request = new CreateUserRequest();

        User user = new User();
        user.setId("501");
        user.setName("John Doe");
        user.setEmail("john.doe@gmail");
        request.setUser(user);
        try {
            CreateUserResponse response = client.createUserWithSoap(request);
            assertNotNull(response);
            assertNotNull(response.getMessage());
            assertTrue(response.getMessage().contains("Success"));
        } catch (SOAPFaultException soapFaultException) {
            fail();
        }

    }

    @Test
    void whenSoapFault_thenThrowSOAPFaultException() {
        JAXBContextFactory jaxbFactory = new JAXBContextFactory.Builder().withMarshallerJAXBEncoding("UTF-8").build();
        SoapClient client = Feign.builder()
          .encoder(new SOAPEncoder(jaxbFactory))
          .errorDecoder(new SOAPErrorDecoder())
          .logger(new Slf4jLogger())
          .logLevel(Logger.Level.FULL)
          .decoder(new SOAPDecoder(jaxbFactory))
          .target(SoapClient.class, "http://localhost:18080/ws/users/");
        CreateUserRequest request = new CreateUserRequest();

        User user = new User();
        user.setId("500");
        user.setName("John Doe");
        user.setEmail("john.doe@gmail");
        request.setUser(user);
        try {
            client.createUserWithSoap(request);
        } catch (SOAPFaultException soapFaultException) {
            assertNotNull(soapFaultException.getMessage());
            assertTrue(soapFaultException.getMessage().contains("This is a reserved user id"));
        }

    }

    private String soapPayload() {
        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:feig=\"http://www.baeldung.com/springbootsoap/feignclient\">\n" + "   <soapenv:Header/>\n" + "   <soapenv:Body>\n" + "      <feig:createUserRequest>\n"
          + "         <feig:user>\n" + "            <feig:id>1</feig:id>\n" + "            <feig:name>john doe</feig:name>\n" + "            <feig:email>john.doe@gmail.com</feig:email>\n" + "         </feig:user>\n" + "      </feig:createUserRequest>\n"
          + "   </soapenv:Body>\n" + "</soapenv:Envelope>";
    }
}
