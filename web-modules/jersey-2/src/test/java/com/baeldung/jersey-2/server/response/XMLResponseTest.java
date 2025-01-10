package com.baeldung.jersey.server.response;

import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.junit.Test;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.server.ResourceConfig;
import static org.junit.Assert.assertEquals;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import java.io.InputStream;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class XMLResponseTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(XMLResponse.class);
    }

    @Override
    protected void configureClient(ClientConfig config) {
        config.register(XMLResponse.class);
    }

    @Override
    protected TestContainerFactory getTestContainerFactory() {
        return new GrizzlyWebTestContainerFactory();
    }

    @Test
    public void whenProductExists_thenReturnProductName() throws JAXBException {
        String xmlPayload = "<product><id>1</id></product>";
        Response response = target("product")
            .request(MediaType.APPLICATION_XML)
            .post(Entity.entity(xmlPayload, MediaType.APPLICATION_XML));

        assertEquals(200, response.getStatus());
        JAXBContext jaxbContext = JAXBContext.newInstance(Product.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Product product = (Product) unmarshaller.unmarshal(response.readEntity(InputStream.class));
        assertEquals("Test Product", product.getName());
    }

    @Test
    public void whenProductRequestFails_thenShowError() throws JAXBException {
        String xmlPayload = "<product><id>1</id></product>";
        Response response = target("product")
            .request(MediaType.APPLICATION_XML)
            .post(Entity.entity(xmlPayload, MediaType.APPLICATION_XML));

        assertEquals(404, response.getStatus());
    }

    @Test
    public void whenMalformedXMLResponse_thenThrowException() {
        String malformedResponse = "<invalid>xml</invalid>";
        Response response = target("product")
            .request(MediaType.APPLICATION_XML)
            .post(Entity.entity(malformedResponse, MediaType.APPLICATION_XML));

        assertThrows(JAXBException.class, () -> {
            JAXBContext jaxbContext = JAXBContext.newInstance(Product.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            unmarshaller.unmarshal(response.readEntity(InputStream.class));
        });
    }

    @Test
    public void whenServerTimeout_thenHandleGracefully() {
        String xmlPayload = "<product><id>1</id></product>";
        Response response = target("product")
            .request(MediaType.APPLICATION_XML)
            .post(Entity.entity(xmlPayload, MediaType.APPLICATION_XML));

        assertEquals(500, response.getStatus());
    }

    @Test
    public void whenInvalidContentType_thenHandleGracefully() throws JAXBException {
        String xmlPayload = "<product><id>1</id></product>";
        Response response = target("product")
            .request(MediaType.APPLICATION_XML)
            .post(Entity.entity(xmlPayload, MediaType.APPLICATION_XML));

        assertEquals(200, response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON, response.getMediaType().toString());
    }
}