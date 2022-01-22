package com.baeldung.keycloaksoap;

import com.baeldung.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import java.util.HashMap;
import java.util.Map;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    @Value("${ws.api.path:/ws/api/v1/*}")
    private String webserviceApiPath;
    @Value("${ws.port.type.name:ProductsPort}")
    private String webservicePortTypeName;
    @Value("${ws.target.namespace:http://www.baeldung.com/springbootsoap/keycloak}")
    private String webserviceTargetNamespace;
    @Value("${ws.location.uri:http://localhost:18080/ws/api/v1/}")
    private String locationUri;

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, webserviceApiPath);
    }

    @Bean(name = "products")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema productsSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName(webservicePortTypeName);
        wsdl11Definition.setTargetNamespace(webserviceTargetNamespace);
        wsdl11Definition.setLocationUri(locationUri);
        wsdl11Definition.setSchema(productsSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema productsSchema() {
        return new SimpleXsdSchema(new ClassPathResource("products.xsd"));
    }

    @Bean
    public Map<String, Product> getProducts()
    {
        Map<String, Product> map = new HashMap<>();
        Product foldsack= new Product();
        foldsack.setId("1");
        foldsack.setName("Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops");
        foldsack.setDescription("Your perfect pack for everyday use and walks in the forest. ");

        Product shirt= new Product();
        shirt.setId("2");
        shirt.setName("Mens Casual Premium Slim Fit T-Shirts");
        shirt.setDescription("Slim-fitting style, contrast raglan long sleeve, three-button henley placket.");

        map.put("1", foldsack);
        map.put("2", shirt);
        return map;
    }

}
