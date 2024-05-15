package com.baeldung.disableautoconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
        SpringDataWebAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, 
        HibernateJpaAutoConfiguration.class})
public class SpringDataJPA {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataJPA.class, args);
    }
}
