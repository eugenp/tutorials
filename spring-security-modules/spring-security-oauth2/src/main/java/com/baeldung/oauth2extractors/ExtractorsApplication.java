package com.baeldung.oauth2extractors;

import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@PropertySource("classpath:default-application.properties")
@SpringBootApplication
@Controller
public class ExtractorsApplication {
    public static void main(String[] args) {
        if (Strings.isEmpty(System.getProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME))) {
            /*System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME,
                    "oauth2-extractors-baeldung");*/
            System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME,
                    "oauth2-extractors-github");
        }

        SpringApplication.run(ExtractorsApplication.class, args);
    }

    @RequestMapping("/")
    public String index() {
        return "oauth2_extractors";
    }

}
