package org.baeldung.cachedrequest;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * To initialize the WebApplication, Please see
 * {@link org.baeldung.spring.config.MainWebAppInitializer}
 */

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = "org.baeldung.cachedrequest")
public class HttpRequestDemoConfig implements WebMvcConfigurer {

}