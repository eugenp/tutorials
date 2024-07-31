package com.baeldung.ignite.spring.config;

import com.baeldung.ignite.spring.dto.EmployeeDTO;
import com.baeldung.ignite.spring.repository.EmployeeRepository;
import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.springdata.repository.config.EnableIgniteRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableIgniteRepositories(basePackageClasses = EmployeeRepository.class)
@ComponentScan(basePackages = "com.baeldung.ignite.spring.repository")
public class SpringDataConfig {

    @Bean
    public Ignite igniteInstance() {
        IgniteConfiguration config = new IgniteConfiguration();
        
        CacheConfiguration cache = new CacheConfiguration("baeldungCache");
        
        cache.setIndexedTypes(Integer.class, EmployeeDTO.class);
        config.setCacheConfiguration(cache);
        return Ignition.start(config);
    }
}
