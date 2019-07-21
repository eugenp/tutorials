package org.baeldung.startup;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.baeldung.startup")
public class SpringStartupConfig {
    
    @Bean(initMethod="init")
    public InitMethodExampleBean exBean() {
        return new InitMethodExampleBean();
    }
}