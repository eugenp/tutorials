package org.baeldung.jdbcauthentication.postgre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application-postgre.properties")
public class PostgreJdbcAuthenticationApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostgreJdbcAuthenticationApplication.class, args);
    }

}
