package com.baeldung.feign.soap;

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

@EnableWs
@Configuration
public class WebServicesConfiguration extends WsConfigurerAdapter {

    @Value("${ws.api.path:/ws/api/v1/*}")
    private String webserviceApiPath;
    @Value("${ws.port.type.name:UsersPort}")
    private String webservicePortTypeName;
    @Value("${ws.target.namespace:http://www.baeldung.com/springbootsoap/feignclient}")
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

    @Bean(name = "users")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema usersSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName(webservicePortTypeName);
        wsdl11Definition.setTargetNamespace(webserviceTargetNamespace);
        wsdl11Definition.setLocationUri(locationUri);
        wsdl11Definition.setSchema(usersSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema userSchema() {
        return new SimpleXsdSchema(new ClassPathResource("users.xsd"));
    }

}
