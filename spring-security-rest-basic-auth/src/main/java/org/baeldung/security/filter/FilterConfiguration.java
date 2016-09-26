package org.baeldung.security.filter;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

import static java.util.Collections.singletonList;

@Configuration
public class FilterConfiguration {

    @Bean
    public FilterRegistrationBean getCustomFilterRegistrationBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new CustomHttpFilter());

        registration.setInitParameters(new HashMap<String, String>() {{
            put("first-init-param", "Hello from ");
            put("second-init-param", "Filter!");
        }});
        registration.setUrlPatterns(singletonList("/user/registration"));

        return registration;
    }

}