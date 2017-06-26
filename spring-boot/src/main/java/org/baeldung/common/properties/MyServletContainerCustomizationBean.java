package org.baeldung.common.properties;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class MyServletContainerCustomizationBean implements EmbeddedServletContainerCustomizer {

    public MyServletContainerCustomizationBean() {

    }

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        container.setPort(8084);
        container.setContextPath("/springbootapp");

        container.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, "/400"));
        container.addErrorPages(new ErrorPage("/errorHeaven"));
    }

}
