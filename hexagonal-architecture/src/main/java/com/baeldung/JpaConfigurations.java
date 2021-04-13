package com.baeldung;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.baeldung.port.outbound.jpa.UserJpaRepository;

@Configuration
@EnableJpaRepositories(basePackageClasses = {UserJpaRepository.class})
public class JpaConfigurations {

}
