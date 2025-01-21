package com.baeldung.jersey.response;

import com.baeldung.jersey.model.Product;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.slf4j.Logger;
import java.io.InputStream;

public class XMLResponse {
    private final Logger logger;
    private final Client client;
    private final String baseUrl;

    public XMLResponse(Client client, Logger logger, String baseUrl) {
        this.client = client;
        this.logger = logger;
        this.baseUrl = baseUrl;
    }

    public Product fetchProductData(int productId) {
        WebTarget target = client.target(baseUrl);
        String xmlPayload = String.format("<product><id>%d</id></product>", productId);

        try (Response response = target.request(MediaType.APPLICATION_XML)
                .post(Entity.entity(xmlPayload, MediaType.APPLICATION_XML))) {

            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                JAXBContext jaxbContext = JAXBContext.newInstance(Product.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                return (Product) unmarshaller.unmarshal(response.readEntity(InputStream.class));
            } else {
                logger.error("Failed to get product data. Status: {}", response.getStatus());
                return null;
            }
        } catch (JAXBException e) {
            logger.error("Error unmarshalling product data", e);
            return null;
        } catch (Exception e) {
            logger.error("Error processing product data", e);
            return null;
        }
    }
}

