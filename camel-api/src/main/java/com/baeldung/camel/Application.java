package com.baeldung.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ComponentScan(basePackages="com.baeldung.camel")
//@ImportResource({ "classpath:spring/camel-context.xml" })
public class Application extends SpringBootServletInitializer {

    @Value("${server.port}")
    String serverPort;
    
    @Value("${baeldung.api.path}")
    String contextPath;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean servlet = new ServletRegistrationBean(new CamelHttpTransportServlet(), contextPath+"/*");
        servlet.setName("CamelServlet");
        return servlet;
    }

    @Component
    class RestApi extends RouteBuilder {

        @Override
        public void configure() {

            CamelContext context = new DefaultCamelContext();

            // Backend remote SOAP Service being proxied by this REST service
            // CxfEndpoint cxfEndpoint = new CxfEndpoint();
            // cxfEndpoint.setCamelContext(context);
            // cxfEndpoint.setAddress(soapBackendServiceEndpointAddress); // remote SOPA endpoint
            // cxfEndpoint.setDefaultOperationName(soapBackendServiceOperationName);
            // cxfEndpoint.setServiceClass(ServicoProtocolo.class);
            // cxfEndpoint.setLoggingFeatureEnabled(true);
            // cxfEndpoint.setDefaultBus(true);
            // cxfEndpoint.setDataFormat(DataFormat.CXF_MESSAGE);

            // log.info("***** using " + soapBackendServiceEndpointAddress + " as the remote endpoint addr");
            // log.info("***** using " + soapBackendServiceOperationName + " as the remote endpoint op name!");

            // add the SOAP Service endpoint into Camel Context to be invoked/consumed by the REST route
            // try {
            // context.addEndpoint("TesteSoapBackendService", cxfEndpoint);
            // } catch (Exception e) {
            // e.printStackTrace();
            // }

            
            // http://localhost:8080/test-services/api-doc
            restConfiguration().contextPath(contextPath) //
                .port(serverPort)
                .enableCORS(true)
                .apiContextPath("/api-doc")
                .apiProperty("api.title", "Test REST API")
                .apiProperty("api.version", "v1")
                .apiProperty("cors", "true") // cross-site
                .apiContextRouteId("doc-api")
                .component("servlet")
                .bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true");
/**            The Rest DSL supports automatic binding json/xml contents to/from POJOs using Camels Data Format. By default the binding mode is off, meaning there is no automatic binding happening for incoming and outgoing messages.

            You may want to use binding if you develop POJOs that maps to your REST services request and response types. This allows you as a developer to work with the POJOs in Java code.
   */         
            
            rest("/api/").description("Teste REST Service")
                .id("api-route")
//                .produces("application/json")
                .consumes("application/json")
                .post("/bean")
//                .get("/hello/{place}")
                .bindingMode(RestBindingMode.json_xml)
                .type(MyBean.class)
                .enableCORS(true)
//                .outType(OutBean.class)

                .to("direct:remoteService");
            
       
            from("direct:remoteService")
                .routeId("direct-route")
                .tracing()
                .log(">>> ${body.id}")
                .log(">>> ${body.name}")
//                .transform().simple("Hello ${in.body.name}")
//                .process(new Processor() {
//                    @Override
//                    public void process(Exchange exchange) throws Exception {
//                        MyBean bodyIn 
//                        = (MyBean) exchange.getIn().getBody();
//                        
//                        String msg = "Hello " + bodyIn.getName();
//                        
//                        OutBean out = new OutBean();
//                        out.setMessage(msg);
//                        
//                        List<Object> toBodyParams = new ArrayList<Object>();
//                        toBodyParams.add(msg);                        
//                        exchange.getIn().setBody(toBodyParams);
//                    }
//                })
                 
                .marshal()
                .json(JsonLibrary.Jackson)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201));
        }
    }
}