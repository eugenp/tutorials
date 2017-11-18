package org.baeldung.common.error;

import org.springframework.boot.web.servlet.ServletRegistrationBean;

import javax.servlet.Servlet;

public class SpringHelloServletRegistrationBean extends ServletRegistrationBean {

    public SpringHelloServletRegistrationBean() {
    }

    public SpringHelloServletRegistrationBean(Servlet servlet, String... urlMappings) {
        super(servlet, urlMappings);
    }
}
