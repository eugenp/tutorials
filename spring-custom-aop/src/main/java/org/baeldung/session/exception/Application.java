package org.baeldung.session.exception;

import org.baeldung.boot.model.Foo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;

@EntityScan(basePackageClasses = Foo.class)
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        System.setProperty("spring.config.name", "exception");
        System.setProperty("spring.profiles.active", "exception");
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public HibernateJpaSessionFactoryBean sessionFactory() {
        return new HibernateJpaSessionFactoryBean();
    }
}
