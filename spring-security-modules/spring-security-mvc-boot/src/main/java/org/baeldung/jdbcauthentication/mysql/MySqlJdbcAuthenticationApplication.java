package org.baeldung.jdbcauthentication.mysql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application-mysql.properties")
public class MySqlJdbcAuthenticationApplication {

    public static void main(String[] args) {
        SpringApplication.run(MySqlJdbcAuthenticationApplication.class, args);
    }

}
