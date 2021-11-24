package com.baeldung.maxhttpheadersize.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan({ "com.baeldung.maxhttpheadersize.*" })
public class MaxHTTPHeaderSizeConfig implements WebMvcConfigurer {

    public MaxHTTPHeaderSizeConfig() {
        super();
    }

}
