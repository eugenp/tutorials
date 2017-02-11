<<<<<<< HEAD:spring-boot/src/main/java/org/baeldung/common/error/SpringHelloServletRegistrationBean.java
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
=======
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
>>>>>>> origin:spring-boot/Spring-Boot/src/main/java/org/baeldung/common/error/SpringHelloServletRegistrationBean.java
