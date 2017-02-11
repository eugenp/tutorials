package org.baeldung.common.error;

import javax.servlet.Servlet;

import org.springframework.boot.context.embedded.ServletRegistrationBean;

public class SpringHelloServletRegistrationBean extends ServletRegistrationBean {

    public SpringHelloServletRegistrationBean() {
    }

    public SpringHelloServletRegistrationBean(Servlet servlet, String... urlMappings) {
        super(servlet, urlMappings);
    }
}
