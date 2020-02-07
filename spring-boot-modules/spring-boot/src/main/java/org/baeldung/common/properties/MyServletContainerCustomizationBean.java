package org.baeldung.common.properties;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class MyServletContainerCustomizationBean implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    public MyServletContainerCustomizationBean() {

    }

    @Override
    public void customize(ConfigurableServletWebServerFactory container) {
        container.setPort(8084);
        container.setContextPath("/springbootapp");

        container.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, "/400"));
        container.addErrorPages(new ErrorPage("/errorHaven"));
    }

}
