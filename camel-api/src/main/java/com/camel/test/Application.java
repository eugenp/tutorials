package com.camel.test;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ImportResource({ "classpath:spring/camel-context.xml" })
public class Application extends SpringBootServletInitializer {

    @Value("${camel.remote.soapBackendServiceEndpointAddress}")
    String soapBackendServiceEndpointAddress;

    @Value("${camel.remote.soapBackendServiceOperationName}")
    String soapBackendServiceOperationName;

    @Value("${server.port}")
    String serverPort;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean servlet = new ServletRegistrationBean(new CamelHttpTransportServlet(), "/test-services/*");
        servlet.setName("CamelServlet");
        return servlet;
    }

    @Component
    class RestApi extends RouteBuilder {

        @Override
        public void configure() {

            CamelContext context = getContext();

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
            restConfiguration().contextPath("/test-services") //
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

            rest("/api/").description("Teste REST Service")
                .id("api-route")
                .produces("application/json")
                .consumes("application/json")
                .post("bean")
                .bindingMode(RestBindingMode.json_xml)
                .type(MyBean.class)
                .enableCORS(true)
//                .outType(OutBean.class)

                .to("direct:remoteService");

            from("direct:remoteService").routeId("direct-route")
                .log(">>> ${body.id}")
                .log(">>> ${body.name}")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        OutBean out = new OutBean();
                        out.setMessage("Hello " 
                            + exchange.getIn().getBody(MyBean.class).getName());
                        
                        exchange.getIn().setBody(out, OutBean.class);
                    }
                })
                .tracing()
                .log(">>> ${body.message}")
                .to("log:foo?level=info")
                .marshal()
                .json(JsonLibrary.Jackson, true);
        }
    }
}