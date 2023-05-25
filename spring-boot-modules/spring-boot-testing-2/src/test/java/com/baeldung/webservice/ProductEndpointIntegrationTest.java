package com.baeldung.webservice;

import static org.mockito.Mockito.when;
import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.noFault;
import static org.springframework.ws.test.server.ResponseMatchers.payload;
import static org.springframework.ws.test.server.ResponseMatchers.validPayload;
import static org.springframework.ws.test.server.ResponseMatchers.xpath;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.webservices.server.WebServiceServerTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.xml.transform.StringSource;

import com.baeldung.webservice.generated.Product;

@WebServiceServerTest
class ProductEndpointIntegrationTest {

    private static final Map<String, String> NAMESPACE_MAPPING = createMapping();

    @Autowired
    private MockWebServiceClient client;

    @MockBean
    private ProductRepository productRepository;

    @Test
    void givenXmlRequest_whenServiceInvoked_thenValidResponse() throws IOException {
        Product product = new Product();
        product.setId("1");
        product.setName("Product 1");

        when(productRepository.findProduct("1")).thenReturn(product);

        StringSource request = new StringSource(
          "<bd:getProductRequest xmlns:bd='http://baeldung.com/spring-boot-web-service'>" +
            "<bd:id>1</bd:id>" +
          "</bd:getProductRequest>"
        );
        StringSource response = new StringSource(
          "<bd:getProductResponse xmlns:bd='http://baeldung.com/spring-boot-web-service'>" +
            "<bd:product>" +
              "<bd:id>1</bd:id>" +
              "<bd:name>Product 1</bd:name>" +
            "</bd:product>" +
          "</bd:getProductResponse>"
        );

        client.sendRequest(withPayload(request))
          .andExpect(noFault())
          .andExpect(validPayload(new ClassPathResource("webservice/products.xsd")))
          .andExpect(xpath("/bd:getProductResponse/bd:product[1]/bd:name", NAMESPACE_MAPPING)
            .evaluatesTo("Product 1"))
          .andExpect(payload(response));
    }

    private static Map<String, String> createMapping() {
        Map<String, String> mapping = new HashMap<>();
        mapping.put("bd", "http://baeldung.com/spring-boot-web-service");
        return mapping;
    }
}
