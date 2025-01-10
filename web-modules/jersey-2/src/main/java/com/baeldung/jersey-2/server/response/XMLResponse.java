package com.baeldung.jersey.server.response;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.InputStream;

public class XMLResponse {
    public static void main(String[] args) throws JAXBException {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://api.example.com/product");
        String xmlPayload = "<product><id>1</id></product>";
        Response response = target.request(MediaType.APPLICATION_XML).post(Entity.entity(xmlPayload, MediaType.APPLICATION_XML));
        try {
            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                JAXBContext jaxbContext = JAXBContext.newInstance(Product.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                Product product = (Product) unmarshaller.unmarshal(response.readEntity(InputStream.class));
                System.out.println("Product Name: " + product.getName());
            } else {
                System.err.println("Failed to get product data");
            }
        } finally {
            response.close();
            client.close();
        }
    }
}

@XmlRootElement
class Product {
    private int id;
    private String name;

    public Product() {}

    public Product(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @XmlElement
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}